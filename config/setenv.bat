rem run tomcat with JMX ability
rem Run Tomcat as admin
rem for remote connection add -Djava.rmi.server.hostname=TomcatServer_IP
set CATALINA_OPTS=-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
