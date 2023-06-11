const song = {
    state: {
        songsList: [], // 当前歌曲列表
        isPlay: false,
        url: "",
        id: "",
        duration: 0, // 音乐时长
        currentTime: 0, // 当前音乐播放位置
        changeTime: 0, // 指定播放时长
        name: '', // 歌曲名称
        artist: '', // 歌手名称
        picUrl: '', // 歌曲图片
        autoNext: true, // 自动下一曲
        lyric: [], // 歌词
        tempList: [], // 单个歌单信息
        listIndex: null, // 当前歌曲在歌单中的位置
        volume: 20, // 默认音量
        playButtonUrl: '#icon-bofang' //播放状态图标
    },
    getters: {
        songsList: state => {
            let songsList = state.songsList;
            if (!songsList) {
                songsList = JSON.parse(window.sessionStorage.getItem("songsList") || null);
            }
            return songsList;
        },
        isPlay: state => {
            let isPlay = state.isPlay
            if (!isPlay) {
                isPlay = JSON.parse(window.sessionStorage.getItem("isPlay") || null);
            }
            return isPlay;
        },
        url: state => {
            let url = state.url
            if (!url) {
                url = JSON.parse(window.sessionStorage.getItem("url") || null);
            }
            return url;
        },
        id: state => {
            let id = state.id
            if (!id) {
                id = JSON.parse(window.sessionStorage.getItem("id") || null);
            }
            return id;
        },
        playButtonUrl: state => {
            let playButtonUrl = state.playButtonUrl
            if (!playButtonUrl) {
                playButtonUrl = JSON.parse(window.sessionStorage.getItem("playButtonUrl") || null);
            }
            return playButtonUrl;
        },
        duration: state => {
            let duration = state.duration
            if (!duration) {
                duration = JSON.parse(window.sessionStorage.getItem("duration") || null);
            }
            return duration;
        },
        currentTime: state => {
            let currentTime = state.currentTime
            if (!currentTime) {
                currentTime = JSON.parse(window.sessionStorage.getItem("currentTime") || null);
            }
            return currentTime;
        },
        changeTime: state => {
            let changeTime = state.changeTime
            if (!changeTime) {
                changeTime = JSON.parse(window.sessionStorage.getItem("changeTime") || null);
            }
            return changeTime;
        },
        name: state => {
            let name = state.name
            if (!name) {
                name = JSON.parse(window.sessionStorage.getItem("name") || null);
            }
            return name;
        },
        artist: state => {
            let artist = state.artist
            if (!artist) {
                artist = JSON.parse(window.sessionStorage.getItem("artist") || null);
            }
            return artist;
        },
        picUrl: state => {
            let picUrl = state.picUrl
            if (!picUrl) {
                picUrl = JSON.parse(window.sessionStorage.getItem("picUrl") || null);
            }
            return picUrl;
        },
        autoNext: state => {
            let autoNext = state.autoNext
            if (!autoNext) {
                autoNext = JSON.parse(window.sessionStorage.getItem("autoNext") || null);
            }
            return autoNext;
        },
        lyric: state => {
            let lyric = state.lyric
            if (!lyric) {
                lyric = JSON.parse(window.sessionStorage.getItem("lyric") || null);
            }
            return lyric;
        },
        tempList: state => {
            let tempList = state.tempList
            if (!tempList) {
                tempList = JSON.parse(window.sessionStorage.getItem("tempList") || null);
            }
            return tempList;
        },
        listIndex: state => {
            let listIndex = state.listIndex
            if (!listIndex) {
                listIndex = JSON.parse(window.sessionStorage.getItem("listIndex") || null);
            }
            return listIndex;
        },
        volume: state => {
            let volume = state.volume
            if (!volume) {
                volume = JSON.parse(window.sessionStorage.getItem("volume") || null);
            }
            return volume;
        }
    },
    mutations: {
        setSongsList: (state, songsList) => {
            state.songsList = songsList;
            window.sessionStorage.setItem("songsList", JSON.stringify(songsList));
        },
        setIsPlay: (state, isPlay) => {
            state.isPlay = isPlay
            window.sessionStorage.setItem("isPlay", JSON.stringify(isPlay));
        },
        setUrl: (state, url) => {
            state.url = url
            window.sessionStorage.setItem("url", JSON.stringify(url));
        },
        setId: (state, id) => {
            state.id = id
            window.sessionStorage.setItem("id", JSON.stringify(id));
        },
        setPlayButtonUrl: (state, playButtonUrl) => {
            state.playButtonUrl = playButtonUrl
            window.sessionStorage.setItem("playButtonUrl", JSON.stringify(playButtonUrl));
        },
        setDuration: (state, duration) => {
            state.duration = duration
            window.sessionStorage.setItem("duration", JSON.stringify(duration));
        },
        setCurrentTime: (state, currentTime) => {
            state.currentTime = currentTime
            window.sessionStorage.setItem("currentTime", JSON.stringify(currentTime));
        },
        setChangeTime: (state, changeTime) => {
            state.changeTime = changeTime
            window.sessionStorage.setItem("changeTime", JSON.stringify(changeTime));
        },
        setName: (state, name) => {
            state.name = name
            window.sessionStorage.setItem("name", JSON.stringify(name));
        },
        setArtist: (state, artist) => {
            state.artist = artist
            window.sessionStorage.setItem("artist", JSON.stringify(artist));
        },
        setPicUrl: (state, picUrl) => {
            state.picUrl = picUrl
            window.sessionStorage.setItem("picUrl", JSON.stringify(picUrl));
        },
        setAutoNext: (state, autoNext) => {
            state.autoNext = autoNext
            window.sessionStorage.setItem("autoNext", JSON.stringify(autoNext));
        },
        setLyric: (state, lyric) => {
            state.lyric = lyric
            window.sessionStorage.setItem("lyric", JSON.stringify(lyric));
        },
        setTempList: (state, tempList) => {
            state.tempList = tempList
            window.sessionStorage.setItem("tempList", JSON.stringify(tempList));
        },
        setListIndex: (state, listIndex) => {
            state.listIndex = listIndex
            window.sessionStorage.setItem("listIndex", JSON.stringify(listIndex));
        },
        setVolume: (state, volume) => {
            state.volume = volume
            window.sessionStorage.setItem("volume", JSON.stringify(volume));
        }
    }
};

export default song