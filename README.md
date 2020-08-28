# Calendar for the ERZ

The city of Zurich publishes CSV data about the garbage collection on
https://data.stadt-zuerich.ch/dataset?q=Entsorgung

This project converts the CSV to iCal format so that it can be imported in any calendar app
such as Google Calendar or Apple Calendar.

## Publish site

```sh
git clone --branch master https://github.com/regisd/erz-cal.git
git clone --branch gh-pages https://github.com/regisd/erz-cal.git erz-cal-pages

cd erz-cal
bazel build //java/info/decamps/erzconverter:gen_entsorgungskalender
cp bazel-bin/java/info/decamps/erzconverter/index.md ../erz-cal-pages
cp -r bazel-bin/java/info/decamps/erzconverter/KARTON ../erz-cal-pages
cp -r bazel-bin/java/info/decamps/erzconverter/PAPIER ../erz-cal-pages
```
