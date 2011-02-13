begin
  require 'rubygems'
  require 'yaml'
  require 'dalli'
rescue LoadError
    puts "==== ERROR ==="
    puts "This script requires:"
    puts " - jruby"
    puts " - rubygems"
    puts " - yaml"
    puts " - dalli"
    puts "Error Message:"
    puts $!.message
    exit
end
#require 'rubygems'
#require 'yaml'
#require 'dalli'
server = ARGV[0]
key = ARGV[1]
begin
  dc = Dalli::Client.new(server)
  orig_output = dc.get(key)
  begin
    output = Marshal.load(orig_output)
  rescue
    output = orig_output
  end
  puts output.to_yaml
rescue
  puts $!.message
  puts "Unable to parse memcache response"
end
