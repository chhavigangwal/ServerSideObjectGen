#!/bin/bash
cd
cd $1
if [ $3 = 'mongodb' ] ; then 
/usr/local/java/jdk1.7.0_45/bin/jar cvf dynamic-mongodb-entity.jar $2
else 
if [ $3 = 'cassandra' ] ; then 
/usr/local/java/jdk1.7.0_45/bin/jar cvf dynamic-cassandra-entity.jar $2
else
echo "none"
fi
fi

