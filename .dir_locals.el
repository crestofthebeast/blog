;;; .dir_locals.el --- Description -*- lexical-binding: t; -*-
;;
;; Copyright (C) 2024 avery
;;
;; Author: avery <avery@arch.fedora>
;; Maintainer: avery <avery@arch.fedora>
;; Created: May 01, 2024
;; Modified: May 01, 2024
;; Version: 0.0.1
;; Keywords: abbrev bib c calendar comm convenience data docs emulations extensions faces files frames games hardware help hypermedia i18n internal languages lisp local maint mail matching mouse multimedia news outlines processes terminals tex tools unix vc wp
;; Homepage: https://github.com/crestofthebeast/.dir_locals
;; Package-Requires: ((emacs "24.3"))
;;
;; This file is not part of GNU Emacs.
;;
;;; Commentary:
;;
;;  Description
;;
;;; Code:
(setq
 (cider-clojure-cli-aliases . "-M:dev")
 (cider-preferred-build-tool . clojure-cli))


(provide '.dir_locals)
;;; .dir_locals.el ends here
