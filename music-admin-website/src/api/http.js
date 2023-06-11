import { get, post, put, del } from "./request";

// 判断是否登录成功
export const getLogin = (params) => post(`user/login`, params);

// 添加歌手
export const addSinger = (params) => post(`singer/add`, params);

// 查询所有歌手
export const getAll = () => get(`singer/all`);

//修改歌手信息
export const updateSinger = (params) => put(`singer/update`, params);

//删除歌手信息
export const deleteSinger = (id) => get(`singer/del?id=${id}`);

// 添加歌曲
export const addSong = (params) => post(`song/add`, params);

// 查询所有歌曲
export const selectAllSongs = () => get(`song/all`);

// 获取对应歌手的歌曲列表
export const getSongBySingerId = (id) => get(`song/singer/details?singerId=${id}`);

// 根据 id 查询对应歌曲
export const getSongById = (id) => get(`song/byId?id=${id}`);

// 根据歌曲名称获取歌曲
export const selectSongBySongName = (name) => get(`song/byName?name=${name}`);

// 修改歌曲信息
export const updateSongInfo = (params) => put(`song/update`, params);

//删除歌曲信息
export const deleteSongById = (id) => get(`song/delete?id=${id}`);

// 添加歌单
export const addSongList = (params) => post(`songList/add`, params);

//获取所有歌单
export const getAllSongList = () => get(`songList/all`);

//修改歌单
export const updateSongList = (params) => put(`songList/update`, params);

// 删除歌单
export const deleteSongList = (id) => get(`songList/delete?id=${id}`);

// 根据id查询歌曲列表
export const listSongDetails = (id) => get(`listSong/byListSongId?listSongId=${id}`);

// 给歌单添加歌曲
export const addSongForSongList = (params) => post(`listSong/add`, params);

// 删除歌单中的歌曲
export const deleteListSong = (songId, listSongId) => get(`listSong/delete/list?songId=${songId}&listSongId=${listSongId}`);

// 查询所有用户
export const getAllConsumer = () => get(`consumer/all`);

// 添加用户
export const addconsumer = (params) => post(`consumer/add`, params);

// 编辑用户
export const updateConsumerInfo = (params) => put(`consumer/update`, params);

// 删除用户
export const deleteConsumerInfo = (id) => get(`consumer/delete?id=${id}`);

// 根据账号修改密码
export const changePasswordByAccount = (params) => put(`consumer/change/password`, params);

// 查找指定用户收藏列表
export const getConsumerFavorite = (id) => get(`favorite/byId?consumerId=${id}`);

// 删除用户收藏的歌曲
export const removeConsumerFavorite = (cid, sid) => get(`favorite/remove?consumerId=${cid}&songId=${sid}`);

// 评论点赞
export const setUp = (params) => put(`comment/update`, params);

// 获取歌单评论列表
export const selectAllCommentBySongListId = (songListId) => get(`comment/bySongListId?songListId=${songListId}`);

// 获取歌曲评论列表
export const selectAllCommentBySongId = (songId) => get(`comment/bySongId?songId=${songId}`);

// 根据id删除评论信息
export const deleteCommentById = (id) => post(`comment/delete?id=${id}`);

// 根据id获取用户
export const selectConsumerById = (id) => get(`consumer/byId?id=${id}`)

// 获取所有轮播图
export const getAllBanner = () => get(`banner/all`);

// 启用/停用轮播图
export const updateBanner = (params) => put(`banner/update`,params);

// 删除轮播图 
export const deleteBanner = (id) => post(`banner/del`,id);