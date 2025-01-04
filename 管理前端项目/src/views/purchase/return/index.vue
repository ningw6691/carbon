<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="退货单号" prop="returnNo">
        <el-input v-model="queryParams.returnNo" placeholder="请输入退货单号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="到货编号" prop="arriveNo">
        <el-input v-model="queryParams.arriveNo" placeholder="请输入到货编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="合同编号" prop="contractNo">
        <el-input v-model="queryParams.contractNo" placeholder="请输入合同编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="供应商" prop="supplierName">
        <el-input v-model="queryParams.supplierName" placeholder="请输入供应商" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <el-form-item label="供应商" prop="supplierId">
        <el-select v-model="queryParams.supplierId" placeholder="请选择供应商" clearable filterable size="small">
          <el-option v-for="dict in supplierIdOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="退货日期">
        <el-date-picker v-model="daterangeReturnDate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button v-no-more-click type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-no-more-click icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-no-more-click type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['purchase:return:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['purchase:return:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-no-more-click type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['purchase:return:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="returnList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="退货单号" align="center" prop="returnNo" />
      <el-table-column label="到货编号" align="center" prop="arriveNo" />
      <el-table-column label="合同编号" align="center" prop="contractNo" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="退货日期" align="center" prop="returnDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.returnDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createBy" :formatter="createByFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['purchase:return:edit']">修改</el-button>
          <el-button v-no-more-click size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['purchase:return:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改采购退货对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" :inline="true">
        <el-form-item label="退货单号" prop="returnNo">
          <el-input v-model="form.returnNo" placeholder="-系统自动生成" disabled />
        </el-form-item>
        <el-form-item label="到货编号" prop="arriveNo">
          <el-input v-model="form.arriveNo" disabled>
            <el-button v-no-more-click slot="append" @click="handleSelectArrive">选择</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="合同编号" prop="contractNo">
          <el-input v-model="form.contractNo" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="供应商" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="" disabled />
        </el-form-item>
        <el-form-item label="退货日期" prop="returnDate">
          <el-date-picker clearable size="small" v-model="form.returnDate" type="date" value-format="yyyy-MM-dd"
            placeholder="选择退货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
        <el-divider content-position="center">退货明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <!-- <el-col :span="1.5">
            <el-button v-no-more-click type="primary" icon="el-icon-plus" size="mini" @click="handleAddScmPurchaseReturnDetail">添加
            </el-button>
          </el-col> -->
          <el-col :span="1.5">
            <el-button v-no-more-click type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteScmPurchaseReturnDetail">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="scmPurchaseReturnDetailList" :row-class-name="rowScmPurchaseReturnDetailIndex"
          @selection-change="handleScmPurchaseReturnDetailSelectionChange" ref="scmPurchaseReturnDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="物料" prop="materialName" width="200"> </el-table-column>
          <el-table-column label="型号" prop="materialModel" width="100"></el-table-column>
          <el-table-column label="规格" prop="materialSpecification" width="200"></el-table-column>
          <el-table-column label="单位" prop="materialUnit"></el-table-column>
          <el-table-column label="生产批号" prop="batchNo" width="150"></el-table-column>
          <el-table-column label="单价" prop="price" width="150"></el-table-column>
          <el-table-column label="订货数量" prop="bookQuantity" width="100"></el-table-column>
          <el-table-column label="到货数量" prop="arriveQuantity" width="100"> </el-table-column>
          <el-table-column label="退货数量" prop="returnQuantity" width="100">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseReturnDetailList.'+scope.$index+'.returnQuantity'"
                :rules="rules.returnQuantity">
                <el-input v-model.number="scope.row.returnQuantity" placeholder="请输入退货数量" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="detailRemark" width="150">
            <template slot-scope="scope">
              <el-form-item :prop="'scmPurchaseReturnDetailList.'+scope.$index+'.detailRemark'"
                :rules="rules.detailRemark">
                <el-input v-model="scope.row.detailRemark" placeholder="请输入备注" />
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-no-more-click type="primary" @click="submitForm">确 定</el-button>
        <el-button v-no-more-click @click="cancel">取 消</el-button>
      </div>

      <!--选择采购到货单-->
      <ArriveSelect :open="arriveSelectOpen" @onArriveCancel="handleArriveCancel"
        @onArriveSelected="handleArriveSelected"></ArriveSelect>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listReturn,
    getReturn,
    delReturn,
    addReturn,
    updateReturn,
    exportReturn
  } from "@/api/purchase/return";

  import {
    getArrive,
  } from "@/api/purchase/arrive";
  import ArriveSelect from "@/components/Purchase/ArriveSelect";

  export default {
    name: "Return",
    components: {
      ArriveSelect,
    },
    data() {
      return {
        arriveSelectOpen: false,
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 子表选中数据
        checkedScmPurchaseReturnDetail: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 采购退货表格数据
        returnList: [],
        // 退货明细表格数据
        scmPurchaseReturnDetailList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 供应商id字典
        supplierIdOptions: [],
        // 退货日期时间范围
        daterangeReturnDate: [],
        // 创建人字典
        createByOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          returnNo: null,
          arriveNo: null,
          contractNo: null,
          supplierId: null,
          returnDate: null,
          orderByColumn: 'create_time',
          isAsc: 'desc',
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          arriveNo: [{
            required: true,
            message: "请选择到货单号",
            trigger: "blur"
          }],
          returnDate: [{
            required: true,
            message: "退货日期不能为空",
            trigger: "blur"
          }],
          returnQuantity: [{
              required: true,
              message: "退货数量不能为空",
              trigger: "blur"
            },

          ],
        }
      };
    },
    created() {
      this.getQueryData("query_supplier").then(res => {
        this.supplierIdOptions = res.data;
      });
      this.getList();
      this.getQueryData("query_user").then(res => {
        this.createByOptions = res.data;
      });
    },
    watch: {
      'scmPurchaseReturnDetailList': {
        handler(newVal, oldVal) {
          this.form.scmPurchaseReturnDetailList = newVal;
        },
        immediate: true,
        deep: true,
      }
    },
    methods: {
      //选择采购到货回调函数
      handleArriveSelected(list) {
        if (list && list.length > 0) {
          let arrive = list[0];
          this.form.arriveId = arrive.id;
          this.form.arriveNo = arrive.arriveNo;
          this.form.contractId = arrive.contractId;
          this.form.contractNo = arrive.contractNo;
          this.form.supplierId = arrive.supplierId;
          this.form.supplierName = arrive.supplierName;
          // 根据合同id获取detailList
          getArrive(arrive.id).then(res => {
            let detailList = res.data.scmPurchaseArriveDetailList
            let list = [];
            detailList.forEach(item => {
              list.push({
                materialId: item.materialId,
                batchNo: item.batchNo,
                materialName: item.materialName,
                materialModel: item.materialModel,
                materialSpecification: item.materialSpecification,
                materialUnit: item.materialUnit,
                price: item.price,
                bookQuantity: item.bookQuantity,
                arriveQuantity: item.arriveQuantity,
                returnQuantity: null,
                materialCode: item.materialCode,
                detailRemark: ''
              })
            });
            this.scmPurchaseReturnDetailList = list;
          })
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
      /** 查询采购退货列表 */
      getList() {
        this.loading = true;
        this.queryParams.params = {};
        if (null != this.daterangeReturnDate && '' != this.daterangeReturnDate) {
          this.queryParams.params["beginReturnDate"] = this.daterangeReturnDate[0];
          this.queryParams.params["endReturnDate"] = this.daterangeReturnDate[1];
        }
        listReturn(this.queryParams).then(response => {
          this.returnList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 创建人字典翻译
      createByFormat(row, column) {
        return this.selectDictLabel(this.createByOptions, row.createBy);
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
          returnNo: null,
          arriveId: null,
          arriveNo: null,
          contractId: null,
          contractNo: null,
          supplierId: null,
          returnDate: null,
          createBy: null,
          createTime: null,
          remark: null,
          scmPurchaseReturnDetailList: [],
        };
        this.scmPurchaseReturnDetailList = [];
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.daterangeReturnDate = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加采购退货";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getReturn(id).then(response => {
          this.form = response.data;
          this.scmPurchaseReturnDetailList = response.data.scmPurchaseReturnDetailList;
          this.open = true;
          this.title = "修改采购退货";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (!this.scmPurchaseReturnDetailList || this.scmPurchaseReturnDetailList.length == 0) {
              this.msgError('请重新选择到货单号，并选择退货物料！');
              return false;
            }
            for (let detail of this.scmPurchaseReturnDetailList) {
              if (detail.returnQuantity > detail.arriveQuantity) {
                this.msgError('退货物料[' + detail.materialName + ']退货数量不能大于到货数量');
                return false;
              }
            }
            if (this.form.id != null) {
              updateReturn(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addReturn(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除采购退货编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delReturn(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 退货明细序号 */
      rowScmPurchaseReturnDetailIndex({
        row,
        rowIndex
      }) {
        row.index = rowIndex + 1;
      },
      /** 退货明细添加按钮操作 */
      handleAddScmPurchaseReturnDetail() {
        let obj = {};
        obj.materialId = "";
        obj.batchNo = "";
        obj.price = "";
        obj.bookQuantity = "";
        obj.arriveQuantity = "";
        obj.returnQuantity = "";
        this.scmPurchaseReturnDetailList.push(obj);
      },
      /** 退货明细删除按钮操作 */
      handleDeleteScmPurchaseReturnDetail() {
        if (this.checkedScmPurchaseReturnDetail.length == 0) {
          this.$alert("请先选择要删除的退货明细数据", "提示", {
            confirmButtonText: "确定",
          });
        } else {
          this.scmPurchaseReturnDetailList.splice(this.checkedScmPurchaseReturnDetail[0].index - 1, 1);
        }
      },
      /** 单选框选中数据 */
      handleScmPurchaseReturnDetailSelectionChange(selection) {
        if (selection.length > 1) {
          this.$refs.scmPurchaseReturnDetail.clearSelection();
          this.$refs.scmPurchaseReturnDetail.toggleRowSelection(selection.pop());
        } else {
          this.checkedScmPurchaseReturnDetail = selection;
        }
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有采购退货数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportReturn(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };

</script>
