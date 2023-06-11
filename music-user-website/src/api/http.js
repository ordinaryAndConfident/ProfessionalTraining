import Axios from "axios";
import { get, post, put } from "./request";



// 查询所有歌手
export const getAll = () => get(`singer/all`);

// 根据性别查询歌手
export const getSingerOfSex = (sex) => get(`singer/bySex?sex=${sex}`);


// 查询所有歌曲
export const selectAllSongs = () => get(`song/all`);

// 获取对应歌手的歌曲列表
export const getSongBySingerId = (id) => get(`song/singer/details?singerId=${id}`);

// 根据 id 查询对应歌曲
export const getSongById = (id) => get(`song/byId?id=${id}`);

// 根据歌曲名称获取歌曲
export const selectSongBySongName = (name) => get(`song/byName?name=${name}`);

// 根据具歌曲名称模糊查询歌曲
export const selectSongLikeName = (name) => get(`song/likeName?songName=${name}`);


//获取所有歌单
export const getAllSongList = () => get(`songList/all`);

// 根据id 查找歌单
export const getSongListById = (id) => get(`songList/byId?id=${id}`);

// 根据id查询歌曲列表
export const listSongDetails = (id) => get(`listSong/byListSongId?listSongId=${id}`);

// 查找包含文字的歌单列表
export const selectSongListByTitle = (title) => get(`songList/likeTitle?title=${title}`);

//根据风格查找歌单
export const selectSongListByStyle = (style) => get(`songList/likeStyle?style=${style}`);

// 查询所有用户
export const getAllConsumer = () => get(`consumer/all`);

// 下载歌曲
export const downloadSong = (url) => Axios({
    method: 'get',
    url: url,
    responseType: 'blob'
});

// 获取验证码
export const getCode = () => post(`consumer/createCode`);

// 用户注册
export const registerConsumer = (params) => post(`consumer/register`, params);

// 修改用户信息
export const updateConsumer = (params) => put(`consumer/update`, params);

// 用户登录
export const loginConsumer = (params) => get(`consumer/login`, params);

// 根据id获取用户
export const selectConsumerById = (id) => get(`consumer/byId?id=${id}`)


// 根据歌单 id 查询对应的歌曲列表
export const getSongListBySongListId = (id) => get(`song/songList/${id}`);

// 用户对歌单评分
export const setRank = (params) => post(`rank/new`, params);

// 获取指定歌单的平均分
export const rankAvg = (songListId) => get(`rank/avg?songListId=${songListId}`);

// 用户登录之后 ，如果已经进行评分则进行展示，否则评分显示0
export const getUserRankScore = (songListId, consumerId) => get(`rank/user/score?songListId=${songListId}&consumerId=${consumerId}`);

// 提交评论
export const setComment = (params) => post(`comment/new`, params);

// 评论点赞
export const setUp = (params) => put(`comment/update`, params);

// 获取歌单评论列表
export const selectAllCommentBySongListId = (songListId) => get(`comment/bySongListId?songListId=${songListId}`);

// 获取歌曲评论列表
export const selectAllCommentBySongId = (songId) => get(`comment/bySongId?songId=${songId}`);


//                           =====================================================

// 收藏歌曲
export const setFavorite = (params) => post(`favorite/new`, params);

// 查找指定用户收藏列表
export const getConsumerFavorite = (id) => get(`favorite/byId?consumerId=${id}`);


// 查询首页轮播图
export const getHomeBanner = () => get(`banner/getTop4`);




