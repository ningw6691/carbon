import * as API from "../api";

export const paramType = {
    'big1': "/carbonReport/emissionChart/percent", //能耗占比
    'big2': "/carbonReport/productPowerReport/year", //能耗总览 
    'big3': "/material/materialInventory/list", //库存预警
    'big4': "/carbonReport/saleStat", //销售统计  -- 孙
    'big5': '/carbonReport/productPowerReport/list',//生产统计 
    'big6': '/carbonReport/salePlan/achieveRate',// 销售计划完成率  -- 孙
    'big7': '/carbonReport/saleCustomer/ranking',// 客户销售排名  -- 孙
    'big8': '/carbonReport/productPowerReport/day',// 今日碳排放
    'big9': '/carbonReport/productPowerReport/month',// 本月碳排放
    'big10': '/carbonReport/productPowerReport/year',// 本年碳排放
    'big11': '/carbonReport/equipmentPowerReport/list',// 设备碳排放排行
    'big12': '/carbonReport/saleOverall',// 销售总览 本年销售额 本月销售额 今日销售额
}
/******************      通用增删改查       ********************* */
/**
 * 通用列表
 * @param {*} param 
 */
export const currentList = (key, param) => {
    return API.GET(paramType[key] + "/list", param)
}
export const currentPage = (key, param) => {
    return API.GET(paramType[key] + "/page", param)
}
/**
 * 查询可选择的列表
 * @param {*} param 
 */
export const currentSelectList = (key, param) => {
    return API.GET(paramType[key] + "/selectList", param)
}


/**
 * 通用新增
 * @param {*} param 
 */
export const currentSave = (key, param) => {
    return API.POST(paramType[key] + "/save", param)
}
/**
 * 通用修改
 * @param {*} param 
 */
export const currentUpdate = (key, param) => {
    return API.POST(paramType[key] + "/update", param)
}

/**
 * 通用删除
 * @param {*} param 
 */
export const currentDelete = (key, param) => {
    return API.POST(paramType[key] + "/delete", param)
}

/**
 * 通用获取所有不分页
 * @param {*} param 
 */
export const currentSelect = (key, param) => {
    return API.GET(paramType[key] + "/select", param)
}

/**
 * 通用GET
 * @param {*} param 
 */
export const currentGET = (key, param) => {
    return API.GET(paramType[key], param)
}
/**
 * 通用POST
 * @param {*} param 
 */
export const currentPOST = (key, param) => {
    return API.POST(paramType[key], param)
}
// 通用接口集合
export const currentApi = {
    currentList,
    currentPage,
    currentSave,
    currentUpdate,
    currentDelete,
    currentSelect,
    currentSelectList,
    currentPOST,
    currentGET
}