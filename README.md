# Ned the Neural Network
## Introduction
Ned is the result of a year-long project that aims to help optometrists in Australia by doing the heavy-lifiting of time-consuming tasks such as image classification.

***Note: this repository was forked from and is the open-source version of Ned without personal information such as patient's images. Whereas all of the functionality exist in the commercial version, only some are present in this open-source version.***

## Motivation
Ned was designed to address one of the most significant problems in Australia: lack of experienced optometrists. Specifically, currently when an optometrist designs an optical len for her patients, she would need to go through a large amount of retina images in order to classify them correctly. Whereas this is not a hard task for an experienced optometrist (but is for an inexperienced one), it is extremely time-consuming.


As the result, Ned was designed to utilise Deep Learning to streamline the process. More specifically, Ned will be trained with large amount of data, then it can be used to classify images and free the optometrists from such task.

Ned contains two main components: image classifcation website and the classifer.

1. **Image classification website:** this is where optometrists can upload images and train the neural network. It also provides statistics such as error rate, current epoch, whether the neural network is still in training or not, etc.
2. **Classifier**: The classifer comprises of the neural network itself and the web server that provides API access to the public. Such APIs include querying for a specific image to see what type it is, what are the current stats of the neural network, etc.

One of the main motivation of this project was learning. As such, the neural network has been implemented _from scratch_ in Java, instead of relying on third-party libraries such as Tensorflow or Keras.

## Technology stack
This project utilised a wide variety of tools such as:

- Java (Dropwizard, JDBI)
- Python
- Gradle
- Git
- Jenkins
- Nginx
- MySQL
- Flyway