FROM alpine:3.11

RUN apk update && apk add \
    maven \
    openjdk11 \
    bash \
    vim && \
    apk add --update docker openrc && \
    rc-update add docker boot

RUN echo "PS1='\n\[\e[32m\]\u \[\e[33m\]in \w\[\e[0m\] \n> '" >> ~/.bashrc
RUN echo 'alias ls="ls --color=none"' >> ~/.bashrc

CMD [ "/usr/local/src/java/start.sh" ]