export const mixin = {
    methods: {
        // 根据相对地址获取绝对地址
        getBaseUrl(url) {
            return `${this.$store.state.HOST}/${url}`
        },
        // 根据性别返回值，转换性别 0 男 1 女 3 保密
        transSex(sex) {
            if (sex == 3) {
                return '保密';
            } else {
                return sex == 0 ? '男' : '女';
            }
        },
        transBirth(birth) {
            return String(birth).substring(0, 10);
        },
        // 上传图片校验
        handleBeforeUpload(file) {
            const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png');
            if (!isJPG) {
                this.$message({
                    message: "上传头像图片只能是jpg或png格式",
                    type: "error",
                    showClose: true,
                });
                return false;
            }
            // 文件大小
            const fileSize = (file.size / 1024 / 1024) < 10;
            if (!fileSize) {
                this.$message({
                    message: "上传头像图片大小不能超过10MB",
                    type: "error",
                    showClose: true,
                });
                return false;
            }
            return true;
        },
        handleBeforeSong(file) {
            const audio = (file.type === 'audio/mpeg') || (file.type === 'audio/mp3');
            if (!audio) {
                this.$message({
                    message: "上传的音乐只能是MP3或MP4格式",
                    type: "error",
                    showClose: true,
                });
                return false;
            }
            // 文件大小
            const fileSize = (file.size / 1024 / 1024) < 20;
            if (!fileSize) {
                this.$message({
                    message: "上传的音乐大小不能超过20MB",
                    type: "error",
                    showClose: true,
                });
                return false;
            }
            return true;
        },
        // 文件上传成功后
        handleSuccess(res) {
            let _this = this;
            if (res.code == 200) {
                _this.selectAll(); // 刷新当前页面
                _this.$message({
                    message: "更新成功",
                    type: "success",
                    showClose: true,
                });
            } else {
                _this.$message({
                    message: "更新失败",
                    type: "error",
                    showClose: true,
                });
            }
        },
        //删除窗口
        handleDelete(id) {
            this.idx = id;
            this.dialogDel = true;
        },
        handleSelectionChange(val) { // 把多选的 id 值 赋值给 selected
            this.selected = val;
        },
        handleSelectedDel(id) {
            this.idx = id;
        },
        deleteSelected() { // 删除多选的项
            if (this.selected.length == 0) {
                this.$notify({
                    message: '请先选择要删除的记录',
                    shadow: true,
                    type: 'warning'
                });
            } else {
                for (const item of this.selected) {
                    this.handleSelectedDel(item.id);
                    this.handleDel();
                }
            }
            this.selected = [];
        },
        handlePagination() {
            // 为了在删除最后一页的最后一条数据时能成功跳转回最后一页的上一页（pageSize=1时不生效）
            let totalPage = Math.ceil((this.currentPage - 1) / this.pageSize)
            let currentPage = this.currentPage > totalPage ? totalPage : this.currentPage
            this.currentPage = currentPage < 1 ? 1 : currentPage
        }
    }
}

