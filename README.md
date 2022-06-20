# Run using docker

Application can be run using docker:<br>
<br>
(sudo) docker pull rururu387/alpha-task:backend-v1.0 <br>
(sudo) docker run -d -it -p 8080:8080 rururu387/alpha-task:backend-v1.0 <br>
<br>
Server can be accessed via 
[frontend client]("https://github.com/rururu387/AlphaTaskFrontend").

# AlphaTaskBackend

This is server is an interlayer. Once queried, it gathers information from 
[Open exchange rates API](https://docs.openexchangerates.org/docs/latest-json) 
and [Giphy API](https://developers.giphy.com/docs/api#quick-start-guide). 
Server returns gif image depending on currency rate change. <br>
If one has to pay more of queried currency to buy USD (base currency), 
it returns one of these gifs: https://giphy.com/search/rich. <br>
On the contrary it returns gifs from this source: https://giphy.com/search/broke <br>
If rates have not changed since yesterday (e.g. at start of the day) it returns 
one of those gifs: https://giphy.com/search/patience

## Build

(sudo) docker build --no-cache -t rururu387/alpha-task:backend-v1.0 .