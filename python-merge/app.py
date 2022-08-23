from flask import Flask, request, make_response, json
from flask_cors import CORS
import subprocess

app = Flask(__name__)
CORS(app)


@app.route("/api/merge", methods=["GET", "OPTIONS"])
def merge():
    if request.method == "OPTIONS":  # CORS preflight
        return _build_cors_preflight_response()
    elif request.method == "GET":  # The actual request following the preflight
        movie_name = request.args.get('movie_name')
        subtitle_name = request.args.get('subtitle_name')

        try:
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
    app.run()
