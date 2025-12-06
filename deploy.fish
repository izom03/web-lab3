#!/usr/bin/env fish

set wildfly ~/servers/wildfly-28.0.0.Final
set buildfile build/libs/web-lab3-1.0.war
set deployfile "$wildfly/standalone/deployments/web-lab.war"

argparse 'b/build' 's/standalone' -- $argv

if set -q _flag_build
    ./gradlew build war
end

cp -f $buildfile $deployfile

if set -q _flag_standalone
    $wildfly/bin/standalone.sh
end
