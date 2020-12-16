!function(e){"function"==typeof define&&define.amd?define(["jquery"],e):e(jQuery)}(function(e){e.fn.tweet=function(t){function r(e,t){if("string"==typeof e){var r=e;for(var a in t){var n=t[a];r=r.split("{"+a+"}").join(null===n?"":n)}return r}return e(t)}function a(t,r){return function(){var a=[];return this.each(function(){a.push(this.replace(t,r))}),e(a)}}function n(e){return e.replace(/</g,"&lt;").replace(/>/g,"^&gt;")}function i(e,t){return e.replace(m,function(e){for(var r=/^[a-z]+:/i.test(e)?e:"http://"+e,a=e,i=0;i<t.length;++i){var s=t[i];if(s.url===r&&s.expanded_url){r=s.expanded_url,a=s.display_url;break}}return'<a href="'+n(r)+'">'+n(a)+"</a>"})}function s(e){return Date.parse(e.replace(/^([a-z]{3})( [a-z]{3} \d\d?)(.*)( \d{4})$/i,"$1,$2$4$3"))}function u(e){var t=function(e){return parseInt(e,10)},r=new Date,a=t((r.getTime()-e)/1e3);return 1>a&&(a=0),{days:t(a/86400),hours:t(a/3600),minutes:t(a/60),seconds:t(a)}}function _(e){return e.days>2?"about "+e.days+" days ago":e.hours>24?"about a day ago":e.hours>2?"about "+e.hours+" hours ago":e.minutes>45?"about an hour ago":e.minutes>2?"about "+e.minutes+" minutes ago":e.seconds>1?"about "+e.seconds+" seconds ago":"just now"}function o(e){return e.match(/^(@([A-Za-z0-9-_]+)) .*/i)?p.auto_join_text_reply:e.match(m)?p.auto_join_text_url:e.match(/^((\w+ed)|just) .*/im)?p.auto_join_text_ed:e.match(/^(\w*ing) .*/i)?p.auto_join_text_ing:p.auto_join_text_default}function l(){var e="https:"===document.location.protocol?"https:":"http:",t=null===p.fetch?p.count:p.fetch,r="&include_entities=1&callback=?";if(p.list)return e+"//"+p.twitter_api_url+"/1/"+p.username[0]+"/lists/"+p.list+"/statuses.json?page="+p.page+"&per_page="+t+r;if(p.favorites)return e+"//"+p.twitter_api_url+"/1/favorites.json?screen_name="+p.username[0]+"&page="+p.page+"&count="+t+r;if(null===p.query&&1===p.username.length)return e+"//"+p.twitter_api_url+"/1/statuses/user_timeline.json?screen_name="+p.username[0]+"&count="+t+(p.retweets?"&include_rts=1":"")+"&page="+p.page+r;var a=p.query||"from:"+p.username.join(" OR from:");return e+"//"+p.twitter_search_url+"/search.json?&q="+encodeURIComponent(a)+"&rpp="+t+"&page="+p.page+r}function c(e,t){return t?"user"in e?e.user.profile_image_url_https:c(e,!1).replace(/^http:\/\/[a-z0-9]{1,3}\.twimg\.com\//,"https://s3.amazonaws.com/twitter_production/"):e.profile_image_url||e.user.profile_image_url}function w(t){var a={};return a.item=t,a.source=t.source,a.screen_name=t.from_user||t.user.screen_name,a.name=t.from_user_name||t.user.name,a.retweet="undefined"!=typeof t.retweeted_status,a.tweet_time=s(t.created_at),a.join_text="auto"===p.join_text?o(t.text):p.join_text,a.tweet_id=t.id_str,a.twitter_base="http://"+p.twitter_url+"/",a.user_url=a.twitter_base+a.screen_name,a.tweet_url=a.user_url+"/status/"+a.tweet_id,a.reply_url=a.twitter_base+"intent/tweet?in_reply_to="+a.tweet_id,a.retweet_url=a.twitter_base+"intent/retweet?tweet_id="+a.tweet_id,a.favorite_url=a.twitter_base+"intent/favorite?tweet_id="+a.tweet_id,a.retweeted_screen_name=a.retweet&&t.retweeted_status.user.screen_name,a.tweet_relative_time=_(u(a.tweet_time)),a.entities=t.entities?(t.entities.urls||[]).concat(t.entities.media||[]):[],a.tweet_raw_text=a.retweet?"RT @"+a.retweeted_screen_name+" "+t.retweeted_status.text:t.text,a.tweet_text=e([i(a.tweet_raw_text,a.entities)]).linkUser().linkHash()[0],a.retweeted_tweet_text=e([i(t.text,a.entities)]).linkUser().linkHash()[0],a.tweet_text_fancy=e([a.tweet_text]).makeHeart()[0],a.avatar_size=p.avatar_size,a.avatar_url=c(a.retweet?t.retweeted_status:t,"https:"===document.location.protocol),a.avatar_screen_name=a.retweet?a.retweeted_screen_name:a.screen_name,a.avatar_profile_url=a.twitter_base+a.avatar_screen_name,a.user=r('<a class="tweet_user" href="{user_url}">{screen_name}</a>',a),a.join=p.join_text?r('<span class="tweet_join">{join_text}</span>',a):"",a.avatar=a.avatar_size?r('<a class="tweet_avatar" href="{avatar_profile_url}"><img src="{avatar_url}" height="{avatar_size}" width="{avatar_size}" alt="{avatar_screen_name}\'s avatar" title="{avatar_screen_name}\'s avatar" border="0"/></a>',a):"",a.time=r('<span class="tweet_time"><a href="{tweet_url}" title="view tweet on twitter">{tweet_relative_time}</a></span>',a),a.text=r('<span class="tweet_text">{tweet_text_fancy}</span>',a),a.retweeted_text=r('<span class="tweet_text">{retweeted_tweet_text}</span>',a),a.reply_action=r('<a class="tweet_action tweet_reply" href="{reply_url}">reply</a>',a),a.retweet_action=r('<a class="tweet_action tweet_retweet" href="{retweet_url}">retweet</a>',a),a.favorite_action=r('<a class="tweet_action tweet_favorite" href="{favorite_url}">favorite</a>',a),a}function d(t,a){var n=e('<ul class="tweet_list">');n.append(e.map(a,function(e){return"<li>"+r(p.template,e)+"</li>"}).join("")).children("li:first").addClass("tweet_first").end().children("li:odd").addClass("tweet_even").end().children("li:even").addClass("tweet_odd"),e(t).empty().append(n),p.intro_text&&n.before('<p class="tweet_intro">'+p.intro_text+"</p>"),p.outro_text&&n.after('<p class="tweet_outro">'+p.outro_text+"</p>"),e(t).trigger("loaded").trigger(0===a.length?"empty":"full"),p.refresh_interval&&window.setTimeout(function(){e(t).trigger("tweet:load")},1e3*p.refresh_interval)}function f(t){var r=e('<p class="loading">'+p.loading_text+"</p>");p.loading_text&&e(t).not(":has(.tweet_list)").empty().append(r),e.getJSON(l(),function(r){var a=e.map(r.results||r,w);a=e.grep(a,p.filter).sort(p.comparator).slice(0,p.count),e(t).trigger("tweet:retrieved",[a])})}var p=e.extend({username:null,list:null,favorites:!1,query:null,avatar_size:null,count:3,fetch:null,page:1,retweets:!0,intro_text:null,outro_text:null,join_text:null,auto_join_text_default:" I said, ",auto_join_text_ed:" I ",auto_join_text_ing:" I am ",auto_join_text_reply:" I replied to ",auto_join_text_url:" I was looking at ",loading_text:null,refresh_interval:null,twitter_url:"twitter.com",twitter_api_url:"api.twitter.com",twitter_search_url:"search.twitter.com",template:"{avatar}{time}{join} {text}",comparator:function(e,t){return t.tweet_time-e.tweet_time},filter:function(){return!0}},t),m=/\b((?:https?:\/\/|www\d{0,3}[.]|[a-z0-9.\-]+[.][a-z]{2,4}\/)(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'".,<>?«»“”‘’]))/gi;return e.extend({tweet:{t:r}}),e.fn.extend({linkUser:a(/(^|[\W])@(\w+)/gi,'$1<span class="at">@</span><a href="http://'+p.twitter_url+'/$2">$2</a>'),linkHash:a(/(?:^| )[\#]+([\w\u00c0-\u00d6\u00d8-\u00f6\u00f8-\u00ff\u0600-\u06ff]+)/gi,' <a href="https://twitter.com/search?q=%23$1&lang=all'+(p.username&&1===p.username.length&&!p.list?"&from="+p.username.join("%2BOR%2B"):"")+'" class="tweet_hashtag">#$1</a>'),makeHeart:a(/(&lt;)+[3]/gi,"<tt class='heart'>&#x2665;</tt>")}),this.each(function(t,r){p.username&&"string"==typeof p.username&&(p.username=[p.username]),e(r).unbind("tweet:render").unbind("tweet:retrieved").unbind("tweet:load").bind({"tweet:load":function(){f(r)},"tweet:retrieved":function(t,a){e(r).trigger("tweet:render",[a])},"tweet:render":function(t,a){d(e(r),a)}}).trigger("tweet:load")})}});
