FROM maven:3.8.1-openjdk-8

RUN echo "PS1='\n\[\e[32m\]\u \[\e[33m\]in \w\[\e[0m\] \n> '" >> ~/.bashrc
RUN echo 'alias ls="ls --color=none"' >> ~/.bashrc

CMD [ "bash" ]