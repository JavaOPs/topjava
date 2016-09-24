# run tomcat with JMX ability as admin
# for remote connection add -Djava.rmi.server.hostname=TomcatServer_IP
export CATALINA_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"