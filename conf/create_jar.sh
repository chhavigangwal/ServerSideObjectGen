#!/bin/bash
cd
cd $1
if [ $3 = 'mongodb' ] ; then 
$JAVA_HOME/bin/jar cvf dynamic-mongodb-entity.jar $2
else 
if [ $3 = 'cassandra' ] ; then 
$JAVA_HOME/bin/jar cvf dynamic-cassandra-entity.jar $2
else
echo "none"
fi
fi

