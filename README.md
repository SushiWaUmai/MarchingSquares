# Marching Squares

Simple Implementation of the [Marching Squares Algorithm](https://en.wikipedia.org/wiki/Marching_squares) using Kotlin.

## Getting started
Press the `r` button to start recoding frames.
After recoding, you can assemble it to a mp4 file using ffmpeg with this command.

```bash
ffmpeg -i img/frame%d.png -c:v libx264 -vf fps=60 -pix_fmt yuv420p out.mp4
```

## License

This project is licensed under the [MIT license](./LICENSE).
