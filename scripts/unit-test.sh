#!/bin/bash
# This script will run the unit test and test coverage for the application.
# The script should display the total coverage for the CI to parse it that
# will generate a badge.
./gradlew :app:testDebugUnitTest jacocoTestReport