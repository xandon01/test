module.exports = {
  devServer: {
    open: true,
    host: '192.168.119.1',
    port: 8080,
    proxy:{
      '/lvdou' :{
        target:'http://192.168.119.1:9000',
        changeOrigin: true,
        pathRewrite: {
          '^/lvdou': '/lvdou'
        }
     }
    }
  }
}
