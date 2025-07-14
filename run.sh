#!/bin/bash

# IL to LD Converter Launch Script

echo "Starting IL to LD Converter..."

# Check if Leiningen is installed
if ! command -v lein &> /dev/null; then
    echo "Error: Leiningen is not installed."
    echo "Please install Leiningen from: https://leiningen.org/"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
echo "Java version: $JAVA_VERSION"

# Check if JavaFX is available (for Java 11+)
if [[ "$JAVA_VERSION" > "1.8" ]]; then
    echo "Java 11+ detected. JavaFX should be included with cljfx."
fi

# Run the application
if [ "$1" = "--console" ]; then
    echo "Running in console mode..."
    lein run --console
else
    echo "Running in GUI mode..."
    lein run
fi
