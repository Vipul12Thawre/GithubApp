package com.github.demo.utils.network

class PageLinks(rawLink: String?) {

    /**  */
    internal var META_REL = "rel" //$NON-NLS-1$
    /**  */
    internal var META_LAST = "last" //$NON-NLS-1$
    /**  */
    internal var META_NEXT = "next" //$NON-NLS-1$
    /**  */
    internal var META_FIRST = "first" //$NON-NLS-1$
    /**  */
    internal var META_PREV = "prev" //$NON-NLS-1$


    /**  */
    internal var HEADER_NEXT = "X-Next" //$NON-NLS-1$
    /**  */
    internal var HEADER_LAST = "X-Last" //$NON-NLS-1$

    /**
     * @return first
     */
    var first: String? = null
        private set
    /**
     * @return last
     */
    var last: String? = null
        private set
    /**
     * @return next
     */
    var next: String? = null
        private set
    /**
     * @return prev
     */
    var prev: String? = null
        private set


    init {
        if (rawLink != null) {
            val links =
                rawLink.split(DELIM_LINKS.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (link in links) {
                val segments = link.split(DELIM_LINK_PARAM.toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                if (segments.size < 2)
                    continue

                var linkPart = segments[0].trim { it <= ' ' }
                if (!linkPart.startsWith("<") || !linkPart.endsWith(">"))
                //$NON-NLS-1$ //$NON-NLS-2$
                    continue
                linkPart = linkPart.substring(1, linkPart.length - 1)

                for (i in 1 until segments.size) {
                    val rel = segments[i].trim { it <= ' ' }.split("=".toRegex())
                        .dropLastWhile { it.isEmpty() }.toTypedArray() //$NON-NLS-1$
                    if (rel.size < 2 || META_REL != rel[0])
                        continue

                    var relValue = rel[1]
                    if (relValue.startsWith("\"") && relValue.endsWith("\""))
                    //$NON-NLS-1$ //$NON-NLS-2$
                        relValue = relValue.substring(1, relValue.length - 1)

                    if (META_FIRST == relValue)
                        first = linkPart
                    else if (META_LAST == relValue)
                        last = linkPart
                    else if (META_NEXT == relValue)
                        next = linkPart
                    else if (META_PREV == relValue)
                        prev = linkPart
                }
            }
        }
    }

    companion object {

        private val DELIM_LINKS = "," //$NON-NLS-1$

        private val DELIM_LINK_PARAM = ";" //$NON-NLS-1$
    }
}