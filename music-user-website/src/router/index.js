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
      component: () => import('@/views/Home.vue')
    },
    {
      path: '/song-list',
      name: 'song-list',
      component: () => import('@/views/SongList.vue')
    },
    {
      path: '/my-music',
      name: 'my-music',
      component: () => import('@/views/MyMusic.vue')
    },
    {
      path: '/singer-list',
      name: 'singer-list',
      component: () => import('@/views/SingerList.vue')
    },
    {
      path: '/search',
      name: 'search',
      component: () => import('@/views/Search.vue')
    },
    {
      path: '/lyric',
      name: 'lyric',
      component: () => import('@/views/Lyric.vue')
    },
    {
      path: '/login-in',
      name: 'login',
      component: () => import('@/views/Login.vue')
    },
    {
      path: '/sign-up',
      name: 'register',
      component: () => import('@/views/Register.vue')
    },
    {
      path: '/person-info',
      name: 'person-info',
      component: () => import('@/views/PersonInfo.vue')
    },
    {
      path: '/singer-album/:id',
      name: 'singer-album',
      component: () => import('@/views/SingerAlbum.vue')
    },
    {
      path: '/song-list-album/:id',
      name: 'song-list-album',
      component: () => import('@/views/SongListAlbum.vue')
    }
  ]
})
