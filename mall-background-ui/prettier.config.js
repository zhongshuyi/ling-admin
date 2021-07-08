// 配置详解 https://www.cnblogs.com/linjunfu/p/10880381.html
module.exports = {
  // 超过最大值换行
  printWidth: 100,
  // tab缩进大小,默认为2
  tabWidth: 2,
  // 使用分号, 默认true
  semi: false,
  // 使用table缩进,关闭则使用空格
  useTabs: false,
  vueIndentScriptAndStyle: true,
  // 使用单引号, 默认false(在jsx中配置无效, 默认都是双引号)
  singleQuote: true,
  // 引用对象中的属性时更改
  quoteProps: 'as-needed',
  // 在对象文字中打印括号之间的空格 { foo: bar }
  bracketSpacing: true,
  // 多行时尽可能打印尾随逗号。（例如，单行数组永远不会得到尾随逗号。）
  trailingComma: 'es5',
  // 多行 JSX 元素放在最后一行的末尾，而不是单独放在下一行（不适用于自闭元素）
  jsxBracketSameLine: false,
  // 在JSX中使用单引号而不是双引号。
  jsxSingleQuote: false,
  // 在单个箭头函数参数周围加上括号。
  arrowParens: 'always',
  // Prettier 可以在文件顶部插入一个特殊的 @format 标记，指定文件格式需要被格式化。
  insertPragma: false,
  // Prettier 可以将自己限制为仅在文件顶部格式化包含特殊注释（称为pragma）的文件。
  requirePragma: false,
  // 是否换行 Prose Wrap
  proseWrap: 'never',
  // 指定 HTML 文件的全局空白区域敏感度
  htmlWhitespaceSensitivity: 'strict',
  // 指定行尾序列
  endOfLine: 'crlf',
  // 格式化开始的字符,从第0个开始
  rangeStart: 0,
};
