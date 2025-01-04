<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="99px">
      <el-form-item label="申请单号" prop="serialNo">
        <el-input v-model="queryParams.serialNo" placeholder="请输入申请单" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="业务类型" prop="bizType">
        <el-select v-model="queryParams.bizType" placeholder="请选择业务类型" clearable filterable size="small">
          <el-option v-for="dict in bizTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <el-select v-model="queryParams.supplierId" placeholder="请选择供应商" clearable filterable size="small">
          <el-option v-for="dict in supplierIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="合同类型" prop="contractType">
        <el-select v-model="queryParams.contractType" placeholder="请选择合同类型" clearable filterable size="small">
          <el-option v-for="dict in contractTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="入库状态" prop="billStatus">
        <el-select v-model="queryParams.billStatus" placeholder="请选择入库状态" clearable filterable size="small">
          <el-option v-for="dict in billStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请人" prop="applyUser">
        <el-select v-model="queryParams.applyUser" placeholder="请选择申请人" clearable filterable size="small">
          <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请日期" prop="applyTime">
        <el-date-picker v-model="daterangeApplyTime" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="申请状态" prop="applyStatus">
        <el-select v-model="queryParams.applyStatus" placeholder="请选择申请状态" clearable filterable size="small">
          <el-option v-for="dict in applyStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核人" prop="auditUser">
        <el-select v-model="queryParams.auditUser" placeholder="请选择审核人" clearable filterable size="small">
          <el-option v-for="dict in auditUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核日期" prop="auditTime">
        <el-date-picker v-model="daterangeAuditTime" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审核状态" clearable filterable size="small">
          <el-option v-for="dict in auditStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['wmsApply:inWarahouseApply:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate" v-hasPermi="['wmsApply:inWarahouseApply:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete" v-hasPermi="['wmsApply:inWarahouseApply:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inWarahouseApplyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="申请单号" align="center" prop="serialNo" width="100" />
      <el-table-column label="关联单据号" align="center" prop="bizBillNo" width="100" />
      <el-table-column label="业务类型" align="center" prop="bizType" :formatter="bizTypeFormat" />
      <el-table-column label="供应商" align="center" prop="supplierId" :formatter="supplierIdFormat" />
      <el-table-column label="合同号" align="center" prop="contractNo" />
      <el-table-column label="合同类型" align="center" prop="contractType" :formatter="contractTypeFormat" />
      <el-table-column label="单据状态" align="center" prop="billStatus" :formatter="billStatusFormat" />
      <el-table-column label="申请类型" align="center" prop="applyType" :formatter="applyTypeFormat" />
      <el-table-column label="申请人" align="center" prop="applyUser" :formatter="applyUserFormat" />
      <el-table-column label="申请日期" align="center" prop="applyTime" width="180">
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.applyTime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请状态" align="center" prop="applyStatus" :formatter="applyStatusFormat" />
      <el-table-column label="审核人" align="center" prop="auditUser" :formatter="auditUserFormat" />
      <el-table-column label="审核日期" align="center" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.auditTime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="auditStatus" :formatter="auditStatusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click v-if="scope.row.auditStatus == '1' && scope.row.billStatus != '1'" size="mini"
            type="text" icon="el-icon-caret-right" @click="handleInWarehouse(scope.row)"
            v-hasPermi="['wmsApply:inWarahouseApply:edit']">入库</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['wmsApply:inWarahouseApply:edit']">修改</el-button>
          <el-button v-no-more-click v-if="scope.row.auditStatus != '1' && scope.row.applyStatus != '1'" size="mini"
            type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['wmsApply:inWarahouseApply:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改入库申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="95px" :inline="true">
        <el-form-item label="申请单号" prop="serialNo">
          <el-input v-model="form.serialNo" disabled />
        </el-form-item>
        <el-form-item label="关联单据号" prop="bizBillNo">
          <el-input v-model="form.bizBillNo" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectBiz" :disabled="form.auditStatus != '1' && form.applyStatus != '1'
              ? false
              : true
              ">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="业务类型" prop="bizType">
          <el-select v-model="form.bizType" placeholder="请选择业务类型" clearable filterable :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true
            ">
            <el-option v-for="dict in bizTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.bizType == '2'" label="供应商" prop="supplierId">
          <el-select v-model="form.supplierId" placeholder="请选择供应商" clearable filterable :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true
            ">
            <el-option v-for="dict in supplierIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.bizType != '1'" label="合同号" prop="contractNo">
          <el-input v-model="form.contractNo" disabled />
        </el-form-item>
        <el-form-item v-if="form.bizType != '1'" label="合同类型" prop="contractType">
          <el-select v-model="form.contractType" placeholder="" disabled>
            <el-option v-for="dict in contractTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请类型" prop="applyType">
          <el-select v-model="form.applyType" placeholder="" disabled>
            <el-option v-for="dict in applyTypeOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请人" prop="applyUser">
          <el-select v-model="form.applyUser" placeholder="" disabled>
            <el-option v-for="dict in applyUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="申请日期" prop="applyTime">
          <el-date-picker size="small" v-model="form.applyTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="申请状态" prop="applyStatus">
          <el-select v-model="form.applyStatus" placeholder="" disabled>
            <el-option v-for="dict in applyStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核人" prop="auditUser">
          <el-select v-model="form.auditUser" placeholder="" disabled>
            <el-option v-for="dict in auditUserOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核日期" prop="auditTime">
          <el-date-picker size="small" v-model="form.auditTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="" disabled>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审核状态" prop="auditStatus">
          <el-select v-model="form.auditStatus" placeholder="" disabled>
            <el-option v-for="dict in auditStatusOptions" :key="dict.dictValue" :label="dict.dictLabel"
              :value="dict.dictValue"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见" prop="auditComment">
          <el-input v-model="form.auditComment" type="textarea" disabled />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" :disabled="form.auditStatus != '1' && form.applyStatus != '1' ? false : true
            " />
        </el-form-item>
        <el-divider content-position="center">入库申请明细信息</el-divider>
        <el-table :data="wmsInWarehouseApplyDetailList" :row-class-name="rowWmsInWarehouseApplyDetailIndex"
          @selection-change="handleWmsInWarehouseApplyDetailSelectionChange" ref="wmsInWarehouseApplyDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="300">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' +
                scope.$index +
                '.materialName'
                " :rules="rules.materialName">
                <el-input v-model="scope.row.materialName" placeholder="" disabled>
                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="仓库" prop="warehouseId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' +
                scope.$index +
                '.warehouseId'
                " :rules="rules.warehouseId">
                <el-select v-model="scope.row.warehouseId" placeholder="请选择仓库" @change="
                  handleWarehouseChange(scope.row.warehouseId, scope.$index)
                  " :disabled="form.auditStatus != '1' && form.applyStatus != '1'
                    ? false
                    : true
                    ">
                  <el-option v-for="dict in warehouseIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
                    :value="dict.dictValue"></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="库区" prop="whRegionId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' +
                scope.$index +
                '.whRegionId'
                " :rules="rules.whRegionId">
                <el-select v-model="scope.row.whRegionId" placeholder="请选择库区" @change="
                  handleWhRegionChange(scope.row.whRegionId, scope.$index)
                  " :disabled="form.auditStatus != '1' && form.applyStatus != '1'
                    ? false
                    : true
                    ">
                  <el-option v-for="dict in scope.row.whRegionOptions" :key="dict.id" :label="dict.name"
                    :value="dict.id" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="库位" prop="whLocationId" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' +
                scope.$index +
                '.whLocationId'
                " :rules="rules.whLocationId">
                <el-select v-model="scope.row.whLocationId" placeholder="请选择库位" :disabled="form.auditStatus != '1' && form.applyStatus != '1'
                  ? false
                  : true
                  ">
                  <el-option v-for="dict in scope.row.whLocationOptions" :key="dict.id" :label="dict.name"
                    :value="dict.id" />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column :label="form.bizType == '5' ? '发货数量' : '到货数量'" prop="totalQuantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' +
                scope.$index +
                '.totalQuantity'
                " :rules="rules.totalQuantity">
                <el-input v-model.number="scope.row.totalQuantity" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="入库数量" prop="inQuantity" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' +
                scope.$index +
                '.inQuantity'
                " :rules="rules.inQuantity">
                <el-input v-model.number="scope.row.inQuantity" placeholder="" disabled />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="型号" prop="materialModel"> </el-table-column>
          <el-table-column label="规格" prop="materialSpecification">
          </el-table-column>
          <el-table-column label="单位" prop="materialUnit"> </el-table-column>
          <el-table-column label="生产批号" prop="batchNo" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' + scope.$index + '.batchNo'
                " :rules="rules.batchNo">
                <el-input v-model="scope.row.batchNo" placeholder="请输入生产批号" :disabled="form.auditStatus != '1' && form.applyStatus != '1'
                  ? false
                  : true
                  " />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="生产日期" prop="productDate" width="240">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' +
                scope.$index +
                '.productDate'
                " :rules="rules.productDate">
                <el-date-picker clearable v-model="scope.row.productDate" type="date" value-format="yyyy-MM-dd"
                  placeholder="请选择生产日期" :disabled="form.auditStatus != '1' && form.applyStatus != '1'
                    ? false
                    : true
                    " />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="生产单位" prop="manufacturer" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'wmsInWarehouseApplyDetailList.' +
                scope.$index +
                '.manufacturer'
                " :rules="rules.manufacturer">
                <el-input v-model="scope.row.manufacturer" placeholder="请输入生产单位" :disabled="form.auditStatus != '1' && form.applyStatus != '1'
                  ? false
                  : true
                  " />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="primary"
          @click="saveForm">保 存</el-button>
        <el-button v-no-more-click v-if="form.auditStatus != '1' && form.applyStatus != '1'" type="warning"
          @click="submitForm">提 交</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--选择物料档案-->
    <MaterialSelect :open="materialSelectOpen" @onCancel="handleMaterialCancel" @onSelected="handleMaterialSelected">
    </MaterialSelect>
    <!--选择采购到货单-->
    <ArriveSelect :open="arriveSelectOpen" @onArriveCancel="handleArriveCancel"
      @onArriveSelected="handleArriveSelected">
    </ArriveSelect>
    <!--选择销售退货单-->
    <ReturnSelect :open="returnSelectOpen" @onReturnCancel="handleReturnCancel"
      @onReturnSelected="handleReturnSelected">
    </ReturnSelect>
    <!--选择生产完工单-->
    <ProductFinishSelect status="3" :open="finishSelectOpen" @onCancel="handleFinishCancel"
      @onSelected="handleFinishSelected"></ProductFinishSelect>
  </div>
</template>

<script>
import {
  listInWarahouseApply,
  getInWarahouseApply,
  delInWarahouseApply,
  addInWarahouseApply,
  updateInWarahouseApply,
  exportInWarahouseApply,
  submitInWarahouseApply,
  inWarehouse,
} from "@/api/wmsApply/inWarahouseApply";
import MaterialSelect from "@/components/Material/MaterialSelect";
import { listWarehouseRegion } from "@/api/warehouse/warehouseRegion";
import { listWarehouseLocation } from "@/api/warehouse/warehouseLocation";
//选择采购到货单
import ArriveSelect from "@/components/Purchase/ArriveSelect";
import { getArrive } from "@/api/purchase/arrive";
//选择销售退货单
import ReturnSelect from "@/components/Sale/ReturnSelect.vue";

import { getReturn } from "@/api/sale/return";
//选择生产完工单
import ProductFinishSelect from "@/components/product/ProductFinishSelect";
export default {
  name: "InWarahouseApply",
  components: {
    MaterialSelect,
    ArriveSelect,
    ReturnSelect,
    ProductFinishSelect,
  },
  data() {
    return {
      material: null,
      currentRowIndex: null,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedWmsInWarehouseApplyDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 入库申请表格数据
      inWarahouseApplyList: [],
      // 入库申请明细表格数据
      wmsInWarehouseApplyDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      materialSelectOpen: false,
      arriveSelectOpen: false,
      returnSelectOpen: false,
      finishSelectOpen: false,
      // 业务类型字典
      bizTypeOptions: [],
      // 供应商字典
      supplierIdOptions: [],
      // 合同类型字典
      contractTypeOptions: [],
      // 申请类型字典
      applyTypeOptions: [],
      // 申请人字典
      applyUserOptions: [],
      // 申请状态字典
      applyStatusOptions: [],
      // 审核人字典
      auditUserOptions: [],
      // 审核状态字典
      auditStatusOptions: [],
      // 单据状态
      billStatusOptions: [],
      warehouseIdOptions: [],
      // 申请日期时间范围
      daterangeApplyTime: [],
      // 审核日期时间范围
      daterangeAuditTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serialNo: null,
        bizBillId: null,
        bizType: null,
        supplierId: null,
        contractId: null,
        contractType: null,
        applyType: "1",
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        orderByColumn: "apply_time",
        isAsc: "desc",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bizBillNo: [
          { required: true, message: "关联单据不能为空", trigger: "blur" },
        ],
        bizType: [
          { required: true, message: "业务类型不能为空", trigger: "blur" },
        ],
        materialName: [
          { required: true, message: "物料不能为空", trigger: "blur" },
        ],
        warehouseId: [
          { required: true, message: "仓库不能为空", trigger: "change" },
        ],
        whLocationId: [
          { required: true, message: "库位不能为空", trigger: "change" },
        ],
        whRegionId: [
          { required: true, message: "库区不能为空", trigger: "change" },
        ],
        totalQuantity: [
          { required: true, message: "到货数量不能为空", trigger: "blur" },
          {
            type: "number",
            min: 1,
            max: 65535,
            message: "到货数量必须为数字值且大小要在1到65535",
          },
        ],
        inQuantity: [
          { required: true, message: "入库数量不能为空", trigger: "blur" },
          {
            type: "number",
            min: 1,
            max: 65535,
            message: "入库数量必须为数字值且大小要在1到65535",
          },
        ],
        batchNo: [
          { required: true, message: "生产批号不能为空", trigger: "blur" },
          { max: 20, message: "生产批号最多20个字符", trigger: "blur" },
        ],
        remark: [{ max: 100, message: "备注最多100个字符", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("event_type").then((response) => {
      this.bizTypeOptions = response.data.filter(
        (item) =>
          item.dictValue == "1" ||
          item.dictValue == "2" ||
          item.dictValue == "5"
      );
    });
    this.getQueryData("query_supplier").then((res) => {
      this.supplierIdOptions = res.data;
    });
    this.getDicts("contract_type").then((response) => {
      this.contractTypeOptions = response.data;
    });
    this.getDicts("wms_apply_type").then((response) => {
      this.applyTypeOptions = response.data;
    });
    this.getQueryData("query_user").then((res) => {
      this.applyUserOptions = res.data;
    });
    this.getDicts("apply_status").then((response) => {
      this.applyStatusOptions = response.data;
    });
    this.getQueryData("query_user").then((res) => {
      this.auditUserOptions = res.data;
    });
    this.getDicts("audit_status").then((response) => {
      this.auditStatusOptions = response.data;
    });
    this.getQueryData("query_warehouse").then((res) => {
      this.warehouseIdOptions = res.data.map((item) => ({
        dictValue: item.dictValue * 1,
        dictLabel: item.dictLabel,
      }));
    });
    this.getDicts("in_warehouse_status").then((response) => {
      this.billStatusOptions = response.data;
    });
  },
  watch: {
    wmsInWarehouseApplyDetailList: {
      handler(newVal, oldVal) {
        this.form.wmsInWarehouseApplyDetailList = newVal;
      },
      immediate: true,
      deep: true,
    },
  },
  methods: {
    //选择完工单回调函数
    handleFinishSelected(bill) {
      this.form.bizBillId = bill.id;
      this.form.bizBillNo = bill.serialNo;
      let list = [];
      list.push({
        materialId: bill.materialId,
        batchNo: bill.batchNo,
        materialName: bill.materialName,
        materialModel: bill.materialModel,
        materialSpecification: bill.materialSpecification,
        materialUnit: bill.materialUnit,
        warehouseId: null,
        whLocationId: null,
        whRegionId: null,
        whRegionOptions: [],
        whLocationOptions: [],
        totalQuantity: bill.finishQuantity,
        inQuantity: bill.finishQuantity,
        materialCode: bill.materialCode,
      });
      this.wmsInWarehouseApplyDetailList = list;
      this.finishSelectOpen = false;
    },
    //选择
    handleSelectFinish() {
      this.finishSelectOpen = true;
    },
    //取消选择
    handleFinishCancel() {
      this.finishSelectOpen = false;
    },
    //选择销售退货单回调函数
    handleReturnSelected(list) {
      if (list && list.length > 0) {
        let bill = list[0];
        this.form.bizBillId = bill.id;
        this.form.bizBillNo = bill.returnNo;
        this.form.contractId = bill.contractId;
        this.form.contractNo = bill.contractNo;
        this.form.contractType = "2";
        // 根据合同id获取detailList
        getReturn(bill.id).then((res) => {
          let detailList = res.data.scmSaleReturnDetailList;
          let list = [];
          detailList.forEach((item) => {
            let deliveryQuantity = item.deliveryQuantity
              ? item.deliveryQuantity
              : 0;
            let returnQuantity = item.returnQuantity ? item.returnQuantity : 0;
            list.push({
              materialId: item.materialId,
              batchNo: item.batchNo,
              materialName: item.materialName,
              materialModel: item.materialModel,
              materialSpecification: item.materialSpecification,
              materialUnit: item.materialUnit,
              warehouseId: null,
              whLocationId: null,
              whRegionId: null,
              whRegionOptions: [],
              whLocationOptions: [],
              totalQuantity: deliveryQuantity,
              inQuantity: returnQuantity,
              materialCode: item.materialCode,
            });
          });
          this.wmsInWarehouseApplyDetailList = list;
        });
      }
      this.returnSelectOpen = false;
    },
    //选择
    handleSelectReturn() {
      this.returnSelectOpen = true;
    },
    //取消选择
    handleReturnCancel() {
      this.returnSelectOpen = false;
    },
    //选择采购到货回调函数
    handleArriveSelected(list) {
      if (list && list.length > 0) {
        let arrive = list[0];
        this.form.bizBillId = arrive.id;
        this.form.bizBillNo = arrive.arriveNo;
        this.form.contractId = arrive.contractId;
        this.form.contractNo = arrive.contractNo;
        this.form.supplierId = arrive.supplierId;
        this.form.contractType = "1";
        // 根据合同id获取detailList
        getArrive(arrive.id).then((res) => {
          let detailList = res.data.scmPurchaseArriveDetailList;
          let list = [];
          detailList.forEach((item) => {
            let arriveQuantity = item.arriveQuantity ? item.arriveQuantity : 0;
            let returnQuantity = item.returnQuantity ? item.returnQuantity : 0;
            list.push({
              materialId: item.materialId,
              batchNo: item.batchNo,
              materialName: item.materialName,
              materialModel: item.materialModel,
              materialSpecification: item.materialSpecification,
              materialUnit: item.materialUnit,
              warehouseId: null,
              whLocationId: null,
              whRegionId: null,
              whRegionOptions: [],
              whLocationOptions: [],
              totalQuantity: arriveQuantity - returnQuantity,
              inQuantity: arriveQuantity - returnQuantity,
              materialCode: item.materialCode,
            });
          });
          this.wmsInWarehouseApplyDetailList = list;
        });
      }
      this.arriveSelectOpen = false;
    },
    //选择
    handleSelectArrive() {
      this.arriveSelectOpen = true;
    },
    //取消计划
    handleArriveCancel() {
      this.arriveSelectOpen = false;
    },
    //入库处理函数
    handleInWarehouse(row) {
      inWarehouse({ id: row.id }).then((res) => {
        this.msgSuccess("入库成功");
        this.getList();
      });
    },
    //选择关联单据号处理函数
    handleSelectBiz() {
      if (!this.form.bizType) {
        this.msgError("请选择业务类型");
        return;
      }
      //生产入库
      if (this.form.bizType == "1") {
        this.handleSelectFinish();
      }
      //采购入库
      if (this.form.bizType == "2") {
        this.handleSelectArrive();
      }
      //退货入库
      if (this.form.bizType == "5") {
        this.handleSelectReturn();
      }
    },
    //选择仓库处理函数
    handleWarehouseChange(val, index) {
      this.wmsInWarehouseApplyDetailList[index].whRegionId = null;
      this.wmsInWarehouseApplyDetailList[index].whLocationId = null;
      if (val) {
        this.getWhRegionOpts(val, index);
      }
    },
    //选择库区处理函数
    handleWhRegionChange(val, index) {
      this.wmsInWarehouseApplyDetailList[index].whLocationId = null;
      if (val) {
        this.getWhLocationOpts(val, index);
      }
    },
    //获取库区选项
    getWhRegionOpts(val, index) {
      let queryParams = {
        warehouseId: val,
      };
      listWarehouseRegion(queryParams).then((res) => {
        this.wmsInWarehouseApplyDetailList[index].whRegionOptions = res.rows;
      });
    },
    //获取库位选项
    getWhLocationOpts(val, index) {
      let queryParams = {
        regionId: val,
      };
      listWarehouseLocation(queryParams).then((res) => {
        this.wmsInWarehouseApplyDetailList[index].whLocationOptions = res.rows;
      });
    },
    //选择物料处理函数
    handleSelectMaterial(index) {
      this.materialSelectOpen = true;
      this.currentRowIndex = index;
    },
    handleMaterialCancel() {
      this.materialSelectOpen = false;
    },
    //选择物料回调函数
    handleMaterialSelected(list) {
      if (list && list.length > 0) {
        this.material = list[0];
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialId =
          this.material.id;
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialName =
          this.material.name;
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialCode =
          this.material.code;
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialModel =
          this.material.model;
        this.wmsInWarehouseApplyDetailList[
          this.currentRowIndex
        ].materialSpecification = this.material.specification;
        this.wmsInWarehouseApplyDetailList[this.currentRowIndex].materialUnit =
          this.material.unit;
      }
      this.materialSelectOpen = false;
    },
    /** 查询入库申请列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeApplyTime && "" != this.daterangeApplyTime) {
        this.queryParams.params["beginApplyTime"] = this.daterangeApplyTime[0];
        this.queryParams.params["endApplyTime"] = this.daterangeApplyTime[1];
      }
      if (null != this.daterangeAuditTime && "" != this.daterangeAuditTime) {
        this.queryParams.params["beginAuditTime"] = this.daterangeAuditTime[0];
        this.queryParams.params["endAuditTime"] = this.daterangeAuditTime[1];
      }
      listInWarahouseApply(this.queryParams).then((response) => {
        this.inWarahouseApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 业务类型字典翻译
    bizTypeFormat(row, column) {
      return this.selectDictLabel(this.bizTypeOptions, row.bizType);
    },
    // 供应商字典翻译
    supplierIdFormat(row, column) {
      return this.selectDictLabel(this.supplierIdOptions, row.supplierId);
    },
    // 合同类型字典翻译
    contractTypeFormat(row, column) {
      return this.selectDictLabel(this.contractTypeOptions, row.contractType);
    },
    // 申请类型字典翻译
    applyTypeFormat(row, column) {
      return this.selectDictLabel(this.applyTypeOptions, row.applyType);
    },
    // 申请人字典翻译
    applyUserFormat(row, column) {
      return this.selectDictLabel(this.applyUserOptions, row.applyUser);
    },
    // 申请状态字典翻译
    applyStatusFormat(row, column) {
      return this.selectDictLabel(this.applyStatusOptions, row.applyStatus);
    },
    // 审核人字典翻译
    auditUserFormat(row, column) {
      return this.selectDictLabel(this.auditUserOptions, row.auditUser);
    },
    // 审核状态字典翻译
    auditStatusFormat(row, column) {
      return this.selectDictLabel(this.auditStatusOptions, row.auditStatus);
    },
    // 单据状态
    billStatusFormat(row, column) {
      return this.selectDictLabel(this.billStatusOptions, row.billStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        serialNo: null,
        bizBillId: null,
        bizBillNo: null,
        bizType: null,
        supplierId: null,
        contractId: null,
        contractNo: null,
        contractType: null,
        applyType: "1",
        applyUser: null,
        applyTime: null,
        applyStatus: null,
        auditUser: null,
        auditTime: null,
        auditStatus: null,
        remark: null,
        auditComment: null,
        wmsInWarehouseApplyDetailList: [],
      };
      this.wmsInWarehouseApplyDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeApplyTime = [];
      this.daterangeAuditTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let filters = selection.filter((item) => item.auditStatus != "1");
      this.ids = filters.map((item) => item.id);
      this.single = filters.length !== 1;
      this.multiple = !filters.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加入库申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getInWarahouseApply(id).then((response) => {
        this.form = response.data;
        response.data.wmsInWarehouseApplyDetailList.forEach((item) => {
          let obj = {
            //库区信息
            whRegionOptions: [],
            //仓库
            whLocationOptions: [],
          };
          let detail = Object.assign(obj, item);
          this.wmsInWarehouseApplyDetailList.push(detail);
        });
        //this.wmsInWarehouseApplyDetailList = response.data.wmsInWarehouseApplyDetailList;
        this.wmsInWarehouseApplyDetailList.forEach((item, index) => {
          this.getWhRegionOpts(item.warehouseId, index);
          this.getWhLocationOpts(item.whRegionId, index);
        });
        this.open = true;
        this.title = "修改入库申请";
      });
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateInWarahouseApply(this.form).then((response) => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInWarahouseApply(this.form).then((response) => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    //提交按钮
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          submitInWarahouseApply(this.form).then((response) => {
            this.msgSuccess("提交成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除入库申请编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delInWarahouseApply(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    /** 入库申请明细序号 */
    rowWmsInWarehouseApplyDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 入库申请明细添加按钮操作 */
    handleAddWmsInWarehouseApplyDetail() {
      let obj = {};
      obj.materialId = "";
      obj.warehouseId = "";
      obj.whLocationId = "";
      obj.whRegionId = "";
      obj.totalQuantity = "";
      obj.inQuantity = "";
      obj.serialNo = "";
      obj.batchNo = "";
      obj.productDate = "";
      obj.materialName = "";
      obj.materialModel = "";
      obj.materialSpecification = "";
      obj.materialUnit = "";
      obj.whRegionOptions = [];
      obj.whLocationOptions = [];
      this.wmsInWarehouseApplyDetailList.push(obj);
    },
    /** 入库申请明细删除按钮操作 */
    handleDeleteWmsInWarehouseApplyDetail() {
      if (this.checkedWmsInWarehouseApplyDetail.length == 0) {
        this.$alert("请先选择要删除的入库申请明细数据", "提示", {
          confirmButtonText: "确定",
        });
      } else {
        this.wmsInWarehouseApplyDetailList.splice(
          this.checkedWmsInWarehouseApplyDetail[0].index - 1,
          1
        );
      }
    },
    /** 单选框选中数据 */
    handleWmsInWarehouseApplyDetailSelectionChange(selection) {
      if (selection.length > 1) {
        this.$refs.wmsInWarehouseApplyDetail.clearSelection();
        this.$refs.wmsInWarehouseApplyDetail.toggleRowSelection(
          selection.pop()
        );
      } else {
        this.checkedWmsInWarehouseApplyDetail = selection;
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有入库申请数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportInWarahouseApply(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        });
    },
  },
};
</script>