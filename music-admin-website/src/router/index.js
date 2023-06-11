import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('@/pages/Login.vue')
    },
    {
      path: '/home',
      name: 'home',
      component: () => import('@/components/Home.vue'),
      children: [
        {
          path: '/info',
          name: 'info',
          component: () => import('@/pages/InfoPage.vue')
        },
        {
          path: '/consumer',
          name: 'consumer',
          component: () => import('@/components/Consumer.vue')
        },
        {
          path: '/singerlist',
          name: 'singerlist',
          component: () => import('@/components/SingerList.vue')
        },
        {
          path: '/songlist',
          name: 'songlist',
          component: () => import('@/components/SongList.vue')
        },
        {
          path: '/song',
          name: 'song',
          component: () => import('@/pages/SongPage.vue')
        },
        {
          path: '/listsong',
          name: 'listsong',
          component: () => import('@/pages/ListSongPage.vue')
        },
        {
          path: '/favorite-list',
          name: 'favorite-list',
          component: () => import('@/pages/FavoriteList.vue')
        },
        {
          path: '/comment-list',
          name: 'comment-list',
          component: () => import('@/pages/CommentList.vue')
        },
        {
          path: '/banner',
          name: 'banner',
          component: () => import('@/pages/Banner.vue')
        }
      ]
    }
  ]
})
