#!/bin/bash
# This script will format the codebase with the proper kotlin formatting
# using set of rules. This formatter rules is a wrapper to ktlint
# https://ktlint.github.io through detekt.
./gradlew detektFormat