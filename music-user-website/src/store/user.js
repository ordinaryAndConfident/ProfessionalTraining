const user = {
    state: {
        consumerId: '',                 //用户id
        nickName: '',               //用户昵称
        avatar: '',               //用户头像
        sex: ''                    // 用户性别
    },
    getters: {
        consumerId: state => {
            let consumerId = state.consumerId
            if (!consumerId) {
                consumerId = JSON.parse(window.sessionStorage.getItem('consumerId'))
            }
            return consumerId
        },
        nickName: state => {
            let nickName = state.nickName
            if (!nickName) {
                nickName = JSON.parse(window.sessionStorage.getItem('nickName'))
            }
            return nickName
        },
        avatar: state => {
            let avatar = state.avatar
            if (!avatar) {
                avatar = JSON.parse(window.sessionStorage.getItem('avatar'))
            }
            return avatar
        },
        sex: state => {
            let sex = state.sex
            if (!sex) {
                sex = JSON.parse(window.sessionStorage.getItem('sex'))
            }
            return sex
        }
    },
    mutations: {
        setConsumerId: (state, consumerId) => {
            state.consumerId = consumerId
            window.sessionStorage.setItem('consumerId', JSON.stringify(consumerId))
        },
        setNickName: (state, nickName) => {
            state.nickName = nickName
            window.sessionStorage.setItem('nickName', JSON.stringify(nickName))
        },
        setCAvatar: (state, avatar) => {
            state.avatar = avatar
            window.sessionStorage.setItem('avatar', JSON.stringify(avatar))
        },
        setSex: (state, sex) => {
            state.sex = sex
            window.sessionStorage.setItem('sex', JSON.stringify(sex))
        }
    }
}

export default user