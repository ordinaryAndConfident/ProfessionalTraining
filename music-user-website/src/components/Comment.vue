<template>
    <div>
        <div class="comment">
            <div class="title-box">
                <h3>
                    <span>评论</span>
                </h3>
            </div>
            <div class="comment-msg">
                <div class="comment-img">
                    <img :src="defaultAvatar" />
                </div>
                <el-input class="comment-input" type="textarea" resize="none"
                :rows="2" placeholder="评论" v-model="commentContent"></el-input>
            </div>
            <div class="btn">
                <el-button type="primary" size="mini" @click="newComment">评论</el-button>
            </div>
            <div class="title">
                <h5>精彩评论：共{{commentList.length}}条</h5>
            </div>
            <ul class="popular" v-for="(item,index) in commentList" :key="index">
                <li>
                    <div class="popular-img">
                        <img :src="attachImageUrl(avatars[index])" alt="">
                    </div>
                    <div class="popular-msg">
                        <ul>
                            <li class="name">
                                {{nickNames[index]}}：
                                <span class="content">{{item.content}}</span>
                            </li>
                            <li class="time">
                                {{attachTime(item.createTime)}}
                            </li>
                        </ul>
                    </div>
                    <div class="up" ref="up" @click="up(item.id,item.up,index)">
                        <svg class="icon">
                            <use xlink:href="#icon-zan"></use>
                        </svg>
                        ({{item.up}})
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'; 
import {mixin} from '@/mixins/utils'
import { selectAllCommentBySongListId, selectAllCommentBySongId, setComment, selectConsumerById, setUp } from '@/api/http'
export default {
    name: 'comment',
    mixins:[mixin],
    props: [
        'playId',    // 歌曲或者歌单Id
        'type'       // 0:歌曲  1:歌单
    ],
    computed: {
        ...mapGetters([
            'id',    // 当前播放列表
            'isLogin',       // 用户登录状态
            'consumerId',           // 登录的用户id'
            'avatar',        // 用户头像
            'isUp'          // 是否已经点赞
        ])
    },
    mounted(){
        this.getCommentList();
    },
    data(){
        return {
            commentContent:'',
            commentList:[],  // 评论列表
            avatars:[],
            nickNames:[],
            defaultAvatar: require('@/assets/img/default_avatar.jpg')
        }
    },
    methods:{
        // 提交评论
        newComment(){
            if(this.isLogin){
                let params = {
                    songId: this.id,
                    songListId: this.playId,
                    consumerId: this.consumerId,
                    type: this.type,
                    content: this.commentContent
                }
                setComment(params).then((res) => {
                    if (res.code == 200) {
                        this.$notify({
                            message: res.msg,
                            type: 'success'
                        });
                        this.commentContent = '';
                        this.getCommentList();
                    } else {
                        this.$notify({
                            message: res.msg,
                            type: 'error'
                        });
                    }
                }).catch((error) => {
                    console.log(error);
                });
                
            } else {
                this.$notify({
                    message: "请先登录后，再进行评论",
                    type: 'warning'
                });
            }
        },
        getCommentList(){
            if(this.type == 0){ // 歌曲评论列表
                selectAllCommentBySongId(this.playId).then((res)=>{
                    if(res.code == 200){
                       this.commentList = res.data;
                        for (let item of res.data) {
                            this.getUsersInfo(item.id);
                        }
                    }
                }).catch((error)=>{
                    console.log(error);
                })
            }else{
                // 歌单评论列表
                selectAllCommentBySongListId(this.playId).then((res) => {
                    if (res.code == 200) {
                        this.commentList = res.data;
                        for(let item of res.data){
                            this.getUsersInfo(item.consumerId);
                        }
                    }
                }).catch((error) => {
                    console.log(error);
                })
            }
        },
        getUsersInfo(id){
            selectConsumerById(id).then((res)=>{
                this.avatars.push(res.data.avatar);
                this.nickNames.push(res.data.nickName);
            }).catch((error)=>{
                console.log(error);
            })
        },
        up(id,up,index){
            if(this.isLogin){
                if (!this.isUp) { // 判断用户是否已经点过赞了
                    let params = {
                        id: id,
                        up: up + 1
                    }
                    setUp(params).then((res) => {
                        this.$refs.up[index].children[0].style.color = '#C20C0C'; // 改变点赞的颜色
                        this.$store.commit("setIsUp",res.data); //不能重复进行点赞
                        this.getCommentList();
                    }).catch((error) => {
                        console.log(error);
                    })
                }else{
                    this.$notify({
                        message: '已经点赞过啦，不能重复点赞哟',
                        type: 'warning'
                    });
                }
            }else{
                this.$notify({
                    message: '请先登录后，再进行点赞!',
                    type: 'error'
                });
            }
        }
    }
}
</script>

<style lang="scss" scoped>
.title-box{
    height: 30px;
    border-bottom: 2px solid #c20c0c;
    margin-bottom: 20px;
}

.btn{
    position: relative;
    float: right;
    padding: 10px;
}

.title{
    margin-top: 30px;
    border-bottom:2px solid #ccc;
    padding: 10px;
}
@import '@/assets/css/comment.scss'
</style>
