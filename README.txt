Memcache Browser
============================

A simple Java application that connects to a given memcache address, displays a
list of keys present in memcache (see limitations, below) and allows them to be
displayed by clicking on the key name.

If required, also attempts to parse the output using a ruby script.

Installation
----------------------------
Unzip the rar file to a directory, run the JAR. Ensure the 'lib' folder and the
memcache-getvalue.rb script are present within the extracted directory. 

Requirements
----------------------------
Application:
 - JRE of some description.
Ruby parser:
 - jruby present in PATH (uses Runtime.exec)
 - rubygems
  - 'dalli' memcache gem
  - 'yaml' (included with rubygems)

Limitations
----------------------------
 - Only returns the first 1MB of keys in memcache.
 - Polls every 15 seconds, and is a 'Blocking call' - it will force everything
   else attempting to access memcache to wait while it returns the list of keys.

License
----------------------------
Released under the included MIT license.

Future Work
----------------------------
 - include 'ruby' script internal to the application run using jruby
 - 'generalize' parsing script to a command to execute rather than hardcoding.
 - remove 'VALUE xx' response lines from memcache output in RAW mode.
