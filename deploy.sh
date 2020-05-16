#!/bin/sh
# Script to build the ICS and deploy to gh-pages
bazel build java/info/decamps/erzconverter:gen_entsorgungskalender
git clone https://github.com/regisd/erz-cal.git --single-branch --branch gh-pages gh-pages
rm -rf gh-pages/*
cp bazel-bin/java/info/decamps/erzconverter/*.ics gh-pages
cd gh-pages
git commit -a
