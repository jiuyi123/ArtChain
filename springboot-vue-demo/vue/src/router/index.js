import { createRouter, createWebHistory } from 'vue-router'
import About from "@/views/About";
import Market from "@/views/Market";
import MyArts from "@/views/MyArts";
import Create from "@/views/Create";
import Contact from "@/views/Contact";
import Layout from '../layout/Layout';
import AdminLayout from '../layout/AdminLayout'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:"/Market",//重定向
    children:[
      {
        path: '/Market',
        name: 'Market',
        component: ()=>import("@/views/Market"),
      },

      {
        path: '/personSpace',
        name: 'personSpace',
        component: ()=>import("@/views/personSpace"),
        children:[
          {
            path: '/personSpace/cart',
            name: 'Cart',
            component: ()=>import("@/views/personSpacePage/Cart"),
          },

          {
            path: '/personSpace/favorite',
            name: 'Favorite',
            component: ()=>import("@/views/personSpacePage/Favorite"),
          },

          {
            path: '/personSpace/myOrder',
            name: 'MyOrder',
            component: ()=>import("@/views/personSpacePage/MyOrder"),
          },

          {
            path: '/personSpace/myWallet',
            name: 'MyWallet',
            component: ()=>import("@/views/personSpacePage/MyWallet"),
          },

          {
            path: '/personSpace/person',
            name: 'Person',
            component: ()=>import("@/views/personSpacePage/Person"),
          },

          {
            path: '/personSpace/setting',
            name: 'Setting',
            component: ()=>import("@/views/personSpacePage/Setting"),
          },
        ]
      },
      {
        path: '/MyArts',
        name: 'MyArts',
        component: MyArts,
      },
      {
        path: '/Create',
        name: 'Create',
        component: Create,
      },
      {
        path: '/Contact',
        name: 'Contact',
        component: Contact,
      },
    ]
  },
  {
    path: '/admin',
    name: 'AdminLayout',
    component: AdminLayout,
    redirect:"/admin/user",//重定向

    children: [
      {
        path:'/admin/user',
        name:'user',
        component: ()=>import("@/views/Admin/StmInfoMagt/User"),
      },
      {
        path:'/admin/artsCheck',
        name:'ArtsCheck',
        component: ()=>import("@/views/Admin/StmInfoMagt/ArtsCheck"),
      },
      {
        path:'/admin/adminInfo',
        name:'AdminInfo',
        component: ()=>import("@/views/Admin/AdminInfo"),
      },
      {
        path:'/admin/orderManage',
        name:'OrderManage',
        component: ()=>import("@/views/Admin/StmInfoMagt/OrderManage"),
      },
      {
        path:'/check/ContentCheck',
        name:'ContentCheck',
        component: ()=>import("@/views/Admin/ThirdPartyCheck/ContentCheck"),
      },
      {
        path:'/check/SimilarityCheck',
        name:'SimilarityCheck',
        component: ()=>import("@/views/Admin/ThirdPartyCheck/SimilarityCheck"),
      },
    ]
  },
// login
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login"),
  },
// register
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/Register"),
  },
//后台管理

  // {
  //   path: '/',
  //   name: 'Home',
  //   component: Home
  // },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: About,
  },

  // {
  //   path: '/personSpace/MyWallet',
  //   name: 'MyWallet',
  //   component: MyWallet,
  // },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router