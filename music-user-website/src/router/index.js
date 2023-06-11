import Vue from 'vue'
import Router from 'vue-router'

const originalPush = Router.prototype.push

Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/pages/Home.vue')
    },
    {
      path: '/song-list',
      name: 'song-list',
      component: () => import('@/pages/SongList.vue')
    },
    {
      path: '/my-music',
      name: 'my-music',
      component: () => import('@/pages/MyMusic.vue')
    },
    {
      path: '/singer-list',
      name: 'singer-list',
      component: () => import('@/pages/SingerList.vue')
    },
    {
      path: '/search',
      name: 'search',
      component: () => import('@/pages/Search.vue')
    },
    {
      path: '/lyric',
      name: 'lyric',
      component: () => import('@/pages/Lyric.vue')
    },
    {
      path: '/login-in',
      name: 'login',
      component: () => import('@/pages/Login.vue')
    },
    {
      path: '/sign-up',
      name: 'register',
      component: () => import('@/pages/Register.vue')
    },
    {
      path: '/person-info',
      name: 'person-info',
      component: () => import('@/pages/PersonInfo.vue')
    },
    {
      path: '/singer-album/:id',
      name: 'singer-album',
      component: () => import('@/pages/SingerAlbum.vue')
    },
    {
      path: '/song-list-album/:id',
      name: 'song-list-album',
      component: () => import('@/pages/SongListAlbum.vue')
    }
  ]
})
