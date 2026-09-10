import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import PortfolioSummary from './PortfolioSummary.vue'

describe('PortfolioSummary', () => {
  it('rendert Gesamtwert und Gewinn als gain (grün) bei positivem G/V', () => {
    const wrapper = mount(PortfolioSummary, {
      props: {
        value: { amount: 1025, currency: 'EUR' },
        pnl: { amount: 125, currency: 'EUR' },
        loading: false,
        error: null,
      },
    })
    expect(wrapper.text()).toContain('1.025,00')
    expect(wrapper.find('.metric-value.gain').exists()).toBe(true)
    expect(wrapper.find('.metric-value.loss').exists()).toBe(false)
  })

  it('markiert Verlust als loss', () => {
    const wrapper = mount(PortfolioSummary, {
      props: {
        value: { amount: 800, currency: 'EUR' },
        pnl: { amount: -100, currency: 'EUR' },
        loading: false,
        error: null,
      },
    })
    expect(wrapper.find('.metric-value.loss').exists()).toBe(true)
  })

  it('zeigt einen Ladezustand', () => {
    const wrapper = mount(PortfolioSummary, {
      props: { value: null, pnl: null, loading: true, error: null },
    })
    expect(wrapper.find('[role="status"]').exists()).toBe(true)
  })
})
