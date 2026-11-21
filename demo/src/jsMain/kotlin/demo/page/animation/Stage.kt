package demo.page.animation

import io.github.shijianjs.ktjs.anime.core.Target
import io.github.shijianjs.ktjs.demo.style.Palette
import io.github.shijianjs.ktjs.dom.element.inlineStyle
import io.github.shijianjs.ktjs.dom.element.invoke
import io.github.shijianjs.ktjs.dom.webcomponent.CustomElementAdapter
import web.cssom.AlignItems
import web.cssom.Border
import web.cssom.ClassName
import web.cssom.Color
import web.cssom.CustomPropertyName
import web.cssom.Display
import web.cssom.LineStyle
import web.cssom.Overflow
import web.cssom.Padding
import web.cssom.Position
import web.cssom.`var`
import web.cssom.px
import web.html.HTMLElement

/** Wraps a DOM element as an anime.js target. */
internal fun HTMLElement.target(): Target = Target(this)

/** The accent the animated nodes are painted with; Lumo's success colour keeps it on-theme. */
internal val ACCENT: Color = `var`(CustomPropertyName<Color>("--lumo-success-color"), Color("rgb(47, 158, 68)"))

/**
 * `<anime-stage>`: the dashed board the animated nodes move inside.
 *
 * It clips its children at the edges, so it is a component of its own: the demos append whatever they
 * animate into it, and its size is the frame those nodes travel in.
 */
internal class Stage : CustomElementAdapter() {
    /**
     * The board's own box. It is painted when the element connects, not in the constructor: a custom
     * element's constructor may not touch the element's attributes, and creating one that does throws
     * "The result must not have attributes".
     */
    override fun connectedCallback() {
        classList.add(STAGE)
        inlineStyle.apply {
            position = Position.relative
            // A fixed board: the nodes travel inside it, so its width has to be known (the page's section
            // body is content-sized, as every Vaadin vertical layout is).
            width = 640.px
            display = Display.flex
            alignItems = AlignItems.flexEnd
            columnGap = 12.px
            minHeight = 170.px
            padding = Padding(16.px, 16.px)
            border = Border(1.px, LineStyle.dashed, Palette.border)
            borderRadius = 12.px
            overflow = Overflow.hidden
        }
    }

    companion object : StageDefaultReference() {
        /** The class the end-to-end suite uses to find the animated boxes; it carries no styling. */
        private val STAGE: ClassName = ClassName("stage")
    }
}
