"use strict";

module.exports = function (grunt) {

    require('load-grunt-tasks')(grunt);

    grunt.initConfig({
        appConfig: {
            // Uygulama context path, js ve css url lerinin başına eklenir
            "contextPath": "/",
            // Source webppp folder
            "webappPath": "src/main/webapp",
            // Production dist folder
            "dist": "target/dist"
        },
        jshint: {
            all: [
                '<%= appConfig.webappPath %>/resources/js/app/*.js',
                '<%= appConfig.webappPath %>/resources/html-parts/{,*/}*.js'
            ]
        },
        clean: {
            dist: {
                files: [
                    {
                        dot: true,
                        src: [
                            '.tmp',
                            '<%= appConfig.dist %>/*'
                        ]
                    }
                ]
            }

        },
        copy: {
            release: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= appConfig.webappPath %>',
                        src: [
                            '*.html',
                            'html-parts/**/*.html',
                            'resources/images/**',
                            'resources/fonts/*',
                            'resources/i18n/*',
                            'resources/docs/*'
                        ],
                        dest: '<%= appConfig.dist %>'
                    }
                ]
            }
        },
        wiredep: {
            // Injects js css from bower.js to index html file
            // Runs in compile time
            task: {
                // Point to the files that should be updated when
                // you run `grunt wiredep`
                src: [
                    '<%= appConfig.webappPath %>/*.html'
                ],
                fileTypes: {
                    html: {
                        replace: {
                            js: '<script src="<%= appConfig.contextPath %>{{filePath}}"></script>',
                            css: '<link rel="stylesheet" href="<%= appConfig.contextPath %>{{filePath}}" />'
                        }
                    }
                },
                options: {
                    // See wiredep's configuration documentation for the options
                    // you may pass:
                    // https://github.com/taptapship/wiredep#configuration

                }
            }
        },
        ngAnnotate: {
            options: {
                singleQuotes: true
            },
            app: {
                files: [
                    {
                        expand: true,
                        cwd: '.tmp/concat/resources/js',
                        src: '*.js',
                        dest: '.tmp/concat/resources/js'
                    }
                ]
            }
        },
        less: {
            compile: {
                options: [{
                    paths: ["<%= appConfig.webappPath %>/resources/css"]
                }],
                files: {
                    "<%= appConfig.webappPath %>/resources/css/webuygulama.css": "<%= appConfig.webappPath %>/resources/css/webuygulama.less"
                }
            }

        },
        useminPrepare: {
            html: '<%= appConfig.webappPath %>/*.html',
            options: {
                dest: '<%= appConfig.dist %>'
            }
        },
        usemin: {
            html: ['<%= appConfig.dist %>/*.html'],
            css: ['<%= appConfig.dist %>/resources/styles/*.css'],
            options: {
                assetsDirs: ['<%= appConfig.dist %>'],
                basedir: ['<%= appConfig.dist %>'],
                blockReplacements: {
                    css: function (block) {
                        return '<link rel="stylesheet" href="' + block.dest + '" />';
                    }
                }
            }
        },
        htmlmin: {
            dist: {
                options: {
                    /*removeCommentsFromCDATA: true,
                     // https://github.com/appConfig/grunt-usemin/issues/44
                     //collapseWhitespace: true,
                     collapseBooleanAttributes: true,
                     removeAttributeQuotes: true,
                     removeRedundantAttributes: true,
                     useShortDoctype: true,
                     removeEmptyAttributes: true,
                     removeOptionalTags: true*/
                    removeComments: true,
                    collapseWhitespace: true,
                    keepClosingSlash: true
                },
                files: [
                    {
                        expand: true,
                        cwd: '<%= appConfig.dist %>',
                        src: ['*.html', 'partials/**/*.html'],
                        dest: '<%= appConfig.dist %>'
                    }
                ]
            }
        },
        concat: {},
        cssmin: {
            options: {
                banner: "/* Copyright Uludag University. Build time <%= grunt.template.today('isoDateTime') %> */",
                keepSpecialComments: 0
            }
        },
        imagemin: {
            dist: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= appConfig.webappPath %>/resources/images',
                        src: '{,*/}*.{gif,jpg,jpeg,png}',
                        dest: '<%= appConfig.dist %>/resources/images'
                    }
                ]
            }
        },
        uglify: {
            options: {
                banner: "/* Copyright Uludag University. Build time <%= grunt.template.today('isoDateTime') %> */"
            }
        },
        replace: {
            // Usemin clears slash on meta tag close, we add this since thymeleaf says it is invalid XHTML
            dist: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= appConfig.dist %>',
                        src: ['*.html'],
                        dest: '<%= appConfig.dist %>'
                    }
                ],
                options: {
                    patterns: [
                        {
                            match: /(<meta.*>)/,
                            replacement: '$1</meta>'
                        }
                    ]
                }

            }
        },
        rev: {
            // Revision assets for cache busting
            dist: {
                files: {
                    src: [
                        '<%= appConfig.dist %>/resources/js/{,*/}*.js',
                        '<%= appConfig.dist %>/html-parts/{,*/}*.js',
                        '<%= appConfig.dist %>/resources/styles/{,*/}*.css',
                        '<%= appConfig.dist %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
                        '<%= appConfig.dist %>/fonts/*'
                    ]
                }
            }
        }
    });

    grunt.registerTask('resolve-deps', [
        'wiredep',
        'less:compile'
    ]);

    grunt.registerTask('imagemin', [
        'imagemin'
    ]);

    grunt.registerTask('build', [
        'resolve-deps',
        'clean:dist',
        'useminPrepare',
        'copy:release',
        'concat',
        'ngAnnotate',
        'cssmin',
        'uglify',
        'rev',
        'usemin',
        'htmlmin'
    ]);
};