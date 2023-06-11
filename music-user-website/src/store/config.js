const config = {
    state: {
        HOST: 'http://127.0.0.1:9527', // 后端接口根目录
        //HOST: 'http://106.55.62.2:9527', // 后端接口根目录
        activeName: '', // 当前访问菜单名称
        showAside: false,    // 是否显示播放中的歌曲列表
        isLogin: false,       // 用户是否处于登录状态
        isUp: false,          // 是否点赞
        isFavorite: false      // 是否收藏 默认没有收藏
    },
    getters: {
        activeName: state => {
            let activeName = state.activeName;
            if (!activeName) {
                activeName = JSON.parse(window.sessionStorage.getItem("activeName"));
            }
            return activeName;
        },
        showAside: state => {
            let showAside = state.showAside;
            showAside = JSON.parse(window.sessionStorage.getItem("showAside"));
            return showAside;
        },
        isLogin: state => {
            let isLogin = state.isLogin;
            isLogin = JSON.parse(window.sessionStorage.getItem("isLogin"));
            return isLogin;
        },
        isUp: state => {
            let isUp = state.isUp;
            isUp = JSON.parse(window.sessionStorage.getItem("isUp"));
            return isUp;
        },
        isFavorite: state => {
            let isFavorite = state.isFavorite;
            isFavorite = JSON.parse(window.sessionStorage.getItem("isFavorite"));
            return isFavorite;
        }
    },
    mutations: {
        setActiveName: (state, activeName) => {
            state.activeName = activeName;
            window.sessionStorage.setItem("activeName", JSON.stringify(activeName));
        },
        setShowAside: (state, showAside) => {
            state.showAside = showAside;
            window.sessionStorage.setItem("showAside", JSON.stringify(showAside));
        },
        setIsLogin: (state, isLogin) => {
            state.isLogin = isLogin;
            window.sessionStorage.setItem("isLogin", JSON.stringify(isLogin));
        },
        setIsUp: (state, isUp) => {
            state.isUp = isUp;
            window.sessionStorage.setItem("isUp", JSON.stringify(isUp));
        },
        setIsFavorite: (state, isFavorite) => {
            state.isFavorite = isFavorite;
            window.sessionStorage.setItem("isFavorite", JSON.stringify(isFavorite));
        }
    }
};

export default config