const { merge } = require('webpack-merge')
const common = require('./webpack.common.js')
const { CleanWebpackPlugin } = require('clean-webpack-plugin')
const { resolve } = require('path')
const glob = require('glob')
const PurgeCSSPlugin = require('purgecss-webpack-plugin')
const { PROJECT_PATH } = require('../constants')

module.exports = merge(common, {
  mode: 'production',
  devtool: 'none', // 生产环境就不需要打印错误日志了
  plugins: [
    new CleanWebpackPlugin(), // 每次打包前都清除之前打包的目录
    new PurgeCSSPlugin({
      paths: glob.sync(`${resolve(PROJECT_PATH, './src')}/**/*.{tsx,scss,less,css}`, { nodir: true }),
    }),
  ],
})
