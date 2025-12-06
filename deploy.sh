#!/usr/bin/env bash

wildfly="$HOME/servers/wildfly-28.0.0.Final"
buildfile="build/libs/web-lab3-1.0.war"
deployfile="$wildfly/standalone/deployments/web-lab.war"

# Обработка флагов
build=false
standalone=false

while [[ $# -gt 0 ]]; do
    case "$1" in
        -b | --build)
            build=true
            shift
            ;;
        -s | --standalone)
            standalone=true
            shift
            ;;
        -bs | -sb)
            build=true
            standalone=true
            shift
            ;;
        --*=* | -*)  # Обработка неизвестных или неподдерживаемых флагов
            echo "Неизвестный аргумент: $1" >&2
            exit 1
            ;;
        *)
            echo "Неизвестный аргумент: $1" >&2
            exit 1
            ;;
    esac
done

# Сборка, если запрошено
if [[ "$build" == true ]]; then
    ./gradlew build war
fi

# Копирование WAR-файла
cp -f "$buildfile" "$deployfile"

# Запуск WildFly, если запрошено
if [[ "$standalone" == true ]]; then
    "$wildfly/bin/standalone.sh"
fi