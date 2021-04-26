FROM ubuntu:bionic

RUN apt-get update && apt-get install -y vim curl docker.io

RUN echo "PS1='\n\[\e[32m\]\u \[\e[33m\]in \w\[\e[0m\] \n> '" >> ~/.bashrc
RUN echo 'alias ls="ls --color=none"' >> ~/.bashrc

CMD [ "bash" ]