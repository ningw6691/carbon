(function (global, factory) {
  if (typeof define === "function" && define.amd) {
    define("VETable/lang/enUS", ["exports"], factory);
  } else if (typeof exports !== "undefined") {
    factory(exports);
  } else {
    var mod = {
      exports: {}
    };
    factory(mod.exports);
    global.VETable = global.VETable || {};
    global.VETable.lang = global.VETable.lang || {};
    global.VETable.lang.enUS = mod.exports.default;
  }
})(typeof globalThis !== "undefined" ? globalThis : typeof self !== "undefined" ? self : this, function (_exports) {
  "use strict";

  Object.defineProperty(_exports, "__esModule", {
    value: true
  });
  _exports.default = void 0;
  var _default = {
    pagination: {
      goto: "Go to",
      page: "",
      itemsPerPage: " / page",
      total: function total(_total) {
        return "Total " + _total;
      },
      prev5: "Previous 5 Pages",
      next5: "Next 5 Pages"
    },
    table: {
      confirmFilter: "Confirm",
      resetFilter: "Reset",
      cut: "Cut",
      copy: "Copy",
      insertRowAbove: "Insert row above",
      insertRowBelow: "Insert row below",
      removeRow: "Remove $1 row",
      emptyRow: "Empty $1 row",
      emptyColumn: "Empty $1 column",
      emptyCell: "Empty cell",
      leftFixedColumnTo: "Left fixed column to",
      cancelLeftFixedColumnTo: "Cancel left fixed column to",
      rightFixedColumnTo: "Right fixed column to",
      cancelRightFixedColumnTo: "Cancel right fixed column to"
    }
  };
  _exports.default = _default;
});