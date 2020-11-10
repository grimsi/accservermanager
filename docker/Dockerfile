FROM    openjdk:8-jre-alpine

ENV     APPVERSION 1.0.0
ENV     PACKAGES  mongodb supervisor

VOLUME  /opt/server
VOLUME  /data/db
WORKDIR /opt/accservermanager

ADD     supervisord.conf /etc/supervisor.conf

RUN     wget https://github.com/grimsi/accservermanager/releases/download/${APPVERSION}/release.${APPVERSION}.zip && \
        unzip release.${APPVERSION}.zip && \
        rm release.${APPVERSION}.zip application.properties start.bat start.sh && \
        mv accservermanager-${APPVERSION}.jar accservermanager.jar

ADD     application.properties /opt/accservermanager/application.properties

RUN     apk update && \
        apk add --update $PACKAGES --no-cache && \
        rm -rf /var/cache/apk/*

EXPOSE  8000
EXPOSE  9554

CMD     ["/usr/bin/supervisord", "-n", "-c", "/etc/supervisor.conf"]
