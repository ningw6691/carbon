(function (global, factory) {
  if (typeof define === "function" && define.amd) {
    define("VETable/lang/zhCN", ["exports"], factory);
  } else if (typeof exports !== "undefined") {
    factory(exports);
  } else {
    var mod = {
      exports: {}
    };
    factory(mod.exports);
    global.VETable = global.VETable || {};
    global.VETable.lang = global.VETable.lang || {};
    global.VETable.lang.zhCN = mod.exports.default;
  }
})(typeof globalThis !== "undefined" ? globalThis : typeof self !== "undefined" ? self : this, function (_exports) {
  "use strict";

  Object.defineProperty(_exports, "__esModule", {
    value: true
  });
  _exports.default = void 0;
  var _default = {
    pagination: {
      goto: "前往",
      page: "页",
      itemsPerPage: " 条/页",
      total: function total(_total) {
        return "\u5171 " + _total + " \u6761";
      },
      prev5: "向前 5 页",
      next5: "向后 5 页"
    },
    table: {
      confirmFilter: "筛选",
      resetFilter: "重置",
      cut: "剪切",
      copy: "复制",
      insertRowAbove: "上方插入行",
      insertRowBelow: "下方插入行",
      removeRow: "删除 $1 行",
      emptyRow: "清空 $1 行",
      emptyColumn: "清空 $1 列",
      emptyCell: "清空单元格",
      leftFixedColumnTo: "左列冻结",
      cancelLeftFixedColumnTo: "取消左列冻结",
      rightFixedColumnTo: "右列冻结",
      cancelRightFixedColumnTo: "取消右列冻结"
    }
  };
  _exports.default = _default;
});