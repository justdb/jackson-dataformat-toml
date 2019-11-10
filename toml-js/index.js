require("core-js/stable");
var TOML = require("@iarna/toml");
function tomlToJson(toml,space){
    var obj = TOML.parse(toml);
    return JSON.stringify(obj,null,space);
}
function jsonToToml(json){
    var obj = JSON.parse(json);
    return TOML.stringify(obj);
}

module.exports= {tomlToJson : tomlToJson,jsonToToml : jsonToToml};

if (typeof GLOBAL != "undefined") {
  GLOBAL.tomlToJson = tomlToJson;
  GLOBAL.jsonToToml = jsonToToml;
}
