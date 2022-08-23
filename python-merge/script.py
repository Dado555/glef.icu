from moviepy.editor import *
from moviepy.video.tools.subtitles import SubtitlesClip
import sys


def _python_merge(subtitles_path, movie_path, output_path):
    try:
        _fix_subtitles(subtitles_path)
        print("Subtitles fixed!")
    except:
        print("Subtitles not fixed!")


    generator = lambda txt: TextClip(txt, font='Arial', fontsize=24, color='white')  # font='Georgia-Regular'

    sub = SubtitlesClip(subtitles_path, generator)
    video = VideoFileClip(movie_path)
    result = CompositeVideoClip([video, sub.set_position(('center', 'bottom'))])

    result.write_videofile(output_path, fps=video.fps, temp_audiofile="temp-audio.m4a", remove_temp=True,
                           codec="libx264", audio_codec="aac")
    print("Movie and subtitles merged!")


def _fix_subtitles(subtitles_path):
    with open(subtitles_path, 'r', encoding="iso-8859-1") as file:
        file_data = file.read()

    transTable = file_data.maketrans("ÆÈæèð", "ĆČćčđšŠžŽ")
    file_data = file_data.translate(transTable)

    with open(subtitles_path, 'w', encoding='utf-8') as file:
        file.write(file_data)
    print("subtitle fixed")


def _get_paths(movie_name, subtitle_name):
    homedir = os.environ['HOME']
    desktop = os.path.join(homedir, "Desktop")

    movie_path = ""
    subtitle_path = ""

    movie, ext = os.path.splitext(movie_name)
    output_path = os.path.join(desktop, movie + "_MERGED" + ext)

    for root, dirs, files in os.walk(desktop):
        if movie_name in files:
            movie_path = os.path.join(root, movie_name)
        for f in files:
            f1 = "".join(f.split()).lower()
            if "".join(subtitle_name.split()).lower() == f1:
                subtitle_path = os.path.join(root, subtitle_name)
        if movie_path != "" and subtitle_path != "":
            break

    return movie_path, subtitle_path, output_path


argv = sys.argv
movie_path, subtitle_path, output_path = _get_paths(argv[1], argv[2])
_python_merge(subtitle_path, movie_path, output_path)
