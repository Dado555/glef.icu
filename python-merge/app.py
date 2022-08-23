from flask import Flask, request, make_response, json
from flask_cors import CORS
import os
import subprocess

app = Flask(__name__)
CORS(app)


@app.route("/")
def hello():
    return "Hello!"


@app.route("/merge", methods=["GET", "OPTIONS"])
def merge():
    if request.method == "OPTIONS":  # CORS preflight
        return _build_cors_preflight_response()
    elif request.method == "GET":  # The actual request following the preflight
        movie_name = request.args.get('movie_name')
        subtitle_name = request.args.get('subtitle_name')

        # if movie_path == "" or subtitle_path == "":
        #     data = {"message": "Movie or subtitle couldn't be found in dir ~/Desktop"}
        #     response = app.response_class(response=json.dumps(data),
        #                                   status=500,
        #                                   mimetype='application/json')
        #     return _configure_cors_for_response(response)

        try:
            # _python_merge(subtitle_path, movie_path, output_path)
            process = subprocess.Popen(['python', 'script.py', movie_name, subtitle_name],
                                       stdout=subprocess.PIPE)
            out, err = process.communicate()
            print(out)
            print(err)
            data = {"message": "Movie and subtitle merged successfully"}
            response = app.response_class(response=json.dumps(data),
                                          status=200,
                                          mimetype='application/json')
            return _configure_cors_for_response(response)
        except:
            data = {"message": "Couldn't merge movie and subtitle"}
            response = app.response_class(response=json.dumps(data),
                                          status=500,
                                          mimetype='application/json')
            return _configure_cors_for_response(response)

    else:
        raise RuntimeError("Can't handle method {}".format(request.method))


def _build_cors_preflight_response():
    response = make_response()
    response.headers.add("Access-Control-Allow-Origin", "*")
    response.headers.add('Access-Control-Allow-Headers', "*")
    response.headers.add('Access-Control-Allow-Methods', "*")
    return response


def _configure_cors_for_response(response):
    response.headers.add("Access-Control-Allow-Origin", "*")
    return response


if __name__ == "__main__":
    # homedir = os.environ['HOME']
    # desktop = os.path.join(homedir, "Desktop")
    # dog = os.path.join(desktop, "Dog (2022) [1080p] [BluRay] [5.1] [YTS.MX]")
    #
    # movie_name = "Dog.2022.1080p.BluRay.x264.AAC5.1 - [YTS.MX].mp4"
    # subtitle_name = "Dog.2022.720p.WEBRip.x264.AAC-[YTS.MX]_sfxy-ocr-mrg.srt"
    #
    # # movie_path = ""
    # # subtitle_path = ""
    # # movie, ext = os.path.splitext(movie_name)
    # # output_path = os.path.join(desktop, movie + "_MERGED" + ext)
    #
    # for root, dirs, files in os.walk(dog):
    #     print(files)
    #     if subtitle_name in files:
    #         print(subtitle_name)
    app.run()
