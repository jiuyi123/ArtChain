// 跨域配置
module.exports =
    {
        devServer:
            {
            //记住，别写错了devServer//设置本地默认端口  选填
            proxy:
                {                 //设置代理，必须填
                    '/api': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
                        target: 'http://39.105.4.145:3000',     //代理的目标地址
                        //target: 'https://www.baidu.com',
                        changeOrigin: true,                  //是否设置同源，输入是的
                        pathRewrite: {                   //路径重写
                            '^/api': ''                 //选择忽略拦截器里面的内容
                        }
                    },
                    '/dataj': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
                        target: 'http://localhost:9095',     //代理的目标地址
                        changeOrigin: true,                  //是否设置同源，输入是的
                        pathRewrite: {                   //路径重写
                            '^/dataj': ''                 //选择忽略拦截器里面的内容
                        }
                    },
                    '/data': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
                        target: 'http://localhost:9091',     //代理的目标地址
                        changeOrigin: true,                  //是否设置同源，输入是的
                        pathRewrite: {                   //路径重写
                            '^/data': ''                 //选择忽略拦截器里面的内容
                        }
                    },
                    '/url': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
                        target: '741026400@qq.com',     //代理的目标地址
                        changeOrigin: true,                  //是否设置同源，输入是的
                        pathRewrite: {                   //路径重写
                            '^/url': ''                 //选择忽略拦截器里面的内容
                        }
                    },
                }
            }
    }
