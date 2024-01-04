import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [
    {
        path: "/",
        redirect: "login"
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/login.vue')
    },
    {
        path: "/common",
        redirect: "query",
        component: () => import('../components/layout-common.vue'),
        children: [
            {
                path: 'query',
                name: 'query',
                component: () => import('../views/common/query.vue')
            }, {
                path: 'detail',
                name: "detail",
                component: () => import("../views/common/detail.vue")
            }, {
                path: 'replace',
                name: "replace",
                component: () => import("../views/common/replace.vue")
            },
        ]
    },
    {
        path: "/user",
        component: () => import('../components/layout-user.vue'),
        children: [
            {
                path: 'userInfo',
                name: 'userInfo',
                component: () => import('../views/user/UserInfo.vue')
            },
            {
                path: 'messageList',
                name: 'messageList',
                component: () => import('../views/user/MessageList.vue')
            },
            {
                path: 'changePwd',
                name: 'changePwd',
                component: () => import('../views/user/ChangePwd.vue')
            },
            {
                path: 'searchHistory',
                name: 'searchHistory',
                component: () => import('../views/user/SearchHistory.vue')
            },
            {
                path: 'favoritePage',
                name: 'favoritePage',
                component: () => import('../views/user/FavoritePage.vue')
            },
            {
                path: 'publishPage',
                name: 'publishPage',
                component: () => import('../views/user/PublishPage.vue')
            },

        ]
    },
    {
        path: "/admin",
        redirect: {"name": "overview"},
        component: () => import('../components/layout-admin.vue'),
        children: [
            {
                path: 'overview',
                name: 'overview',
                component: () => import('../views/admin/overview.vue')
            }, {
                path: 'user-maintain',
                name: "user-maintain",
                component: () => import("../views/admin/user-maintain.vue")
            }, {
                path: 'page-verify',
                name: "page-verify",
                component: () => import("../views/admin/page-verify")
            }, {
                path: 'test',
                name: "test",
                component: () => import("../views/admin/test")
            },
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
