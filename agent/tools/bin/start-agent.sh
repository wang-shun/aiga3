#!/bin/sh
#
echo "run agent"

PRG="$0"
echo "$PRG"
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  echo "$ls"
  link=`expr "$ls" : '.*-> \(.*\)$'`
  echo "$link"
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`
echo "$PRGDIR"

echo "java -cp $PRGDIR/../:$PRGDIR/../lib/* com.ai.aiga.agent.Bootstrap"

java -cp $PRGDIR/../:$PRGDIR/../lib/* com.ai.aiga.agent.Bootstrap &