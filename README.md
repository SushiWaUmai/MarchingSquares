# Marching Squares

Simple Implementation of the [Marching Squares Algorithm](https://en.wikipedia.org/wiki/Marching_squares) using Kotlin.

## Getting started
Press the `r` button to start recoding frames.
After recoding, you can assemble it to a mp4 file using ffmpeg with this command.

```bash
ffmpeg -i img/frame%d.png -c:v libx264 -vf fps=60 -pix_fmt yuv420p out.mp4
```

## Previews

Metaballs:

https://user-images.githubusercontent.com/54822569/173251480-c6091a57-6a01-438b-8a86-98b5f17403fc.mp4

Perlin Noise:

https://user-images.githubusercontent.com/54822569/173251538-6ec7add5-d7ad-4448-99fe-64a62e64dbc1.mp4

## License

This project is licensed under the [MIT license](./LICENSE).
