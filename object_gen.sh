#!/bin/bash
#"Server Side Object Generation"
# usage: sh object_gen.sh [-file] [JSON file path with schema Definition] [-tomcatPath] [Path to home folder of Tomcat] [-outputPath] [Path where output files are to be put]
if [ $# -gt 6 ];then
echo "Server Side Object Generation"
echo "usage: sh object_gen.sh [-file] [JSON file path with schema Definition] [-tomcatPath] [Path to home folder of Tomcat] [-outputPath] [Path where output files are to be put]"

else if [ $# -lt 6 ];then
echo "Server Side Object Generation"
echo "usage: sh object_gen.sh [-file] [JSON file path with schema Definition] [-tomcatPath] [Path to home folder of Tomcat] [-outputPath] [Path where output files are to be put]"

else 
if [ $1 = "-file" && $3 = "-tomcatPath" && $5 = "-outputPath" ]; then
fileName=$2
tomcatHome=$4
outputPath=$6
echo $fileName
echo $tomcatHome
echo $outputPath
java -jar ./ServerSideObjectGen-0.0.1-jar-with-dependencies.jar $fileName $outputPath
cp $outputPath/META-INF/persistence.xml $tomcatHome/webapps/KunderaJSRest/WEB-INF/classes/META-INF
cp $outputPath/dynamic-entity.jar $tomcatHome/webapps/KunderaJSRest/WEB-INF/lib
$tomcatHome/bin/shutdown.sh
$tomcatHome/bin/startup.sh
echo "done"
else
echo "Server Side Object Generation"
echo "usage: sh object_gen.sh [-file] [JSON file path with schema Definition] [-tomcatPath] [Path to home folder of Tomcat] [-outputPath] [Path where output files are to be put]"

fi
fi
fi
