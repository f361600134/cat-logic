(function(mod) {
	if (typeof exports == "object" && typeof module == "object") // CommonJS
		mod(require("../../lib/codemirror"));
	else if (typeof define == "function" && define.amd) // AMD
		define(["../../lib/codemirror"], mod);
	else // Plain browser env
		mod(CodeMirror);
})(function(CodeMirror) {
	"use strict";

	CodeMirror.defineMode("zproto", function(config, parserConfig) {
		var jsonldMode = parserConfig.jsonld;
		var isOperatorChar = /[+\-*&%=<>!?|~^@]/;

		function parseWords(str) {
			var obj = {},
				words = str.split(" ");
			for (var i = 0; i < words.length; ++i) obj[words[i]] = true;
			return obj;
		}

		// 关键字
		var keywords = parseWords("s ss sss");

		var type, content;

		function ret(tp, style, cont) {
			type = tp;
			content = cont;
			return style;
		}

		function tokenBase(stream, state) {
			var beforeParams = state.beforeParams;
			state.beforeParams = false;
			var ch = stream.next();

			if (ch == '"' || ch == "'") {
				state.tokenize = tokenString(ch);
				return state.tokenize(stream, state);
			} else if (ch == "." && stream.match(/^\d[\d_]*(?:[eE][+\-]?[\d_]+)?/)) {
				return ret("number", "number");
			} else if (ch == '[') {
				stream.skipTo(']');
				stream.eat(']');
				return ret("string", "string");
			} else if (/\d/.test(ch)) {
				stream.eatWhile(/[\w\.]/);
				return "number";
			} else {
				stream.eatWhile(/[\w\$_{}\xa1-\uffff]/);
				var word = stream.current();
				if (keywords && keywords.propertyIsEnumerable(word)) {
					state.beforeParams = true;
					return "keyword";
				}

				return null;
			}
		}

		function tokenString(quote) {
			return function(stream, state) {
				var escaped = false,
					next;
				if (jsonldMode && stream.peek() == "@" && stream.match(isJsonldKeyword)) {
					state.tokenize = tokenBase;
					return ret("jsonld-keyword", "meta");
				}
				while ((next = stream.next()) != null) {
					if (next == quote && !escaped) break;
					escaped = !escaped && next == "\\";
				}
				if (!escaped) state.tokenize = tokenBase;
				return ret("string", "string");
			};
		}

		return {
			startState: function() {
				return {
					tokenize: tokenBase,
					beforeParams: false,
					inParams: false
				};
			},
			token: function(stream, state) {
				if (stream.eatSpace()) return null;
				return state.tokenize(stream, state);
			}
		};

	});

	CodeMirror.registerHelper("hint", "zproto", function(cm) {
		//自动补全
		var hintList = ['s', 'ss', 'sss'];

		var cur = cm.getCursor(),
			token = cm.getTokenAt(cur);
		var start = token.start,
			end = cur.ch
		var str = token.string

		var list = hintList.filter(function(item) {
			return item.indexOf(str) === 0
		})

		if (list.length) return {
			list: list,
			from: CodeMirror.Pos(cur.line, start),
			to: CodeMirror.Pos(cur.line, end)
		};
	});

	CodeMirror.defineMIME("text/x-zproto", "zproto");

});