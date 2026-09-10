import { describe, it, expect, vi, beforeEach } from 'vitest'
import { flushPromises, mount } from '@vue/test-utils'
import AddPositionForm from './AddPositionForm.vue'
import * as api from '../api/portfolio'

vi.mock('../api/portfolio', () => ({ addPosition: vi.fn() }))

describe('AddPositionForm', () => {
  beforeEach(() => vi.clearAllMocks())

  it('zeigt Validierungsfehler bei leerer Eingabe und ruft die API nicht', async () => {
    const wrapper = mount(AddPositionForm)
    await wrapper.find('form').trigger('submit.prevent')

    expect(wrapper.find('.msg.error').exists()).toBe(true)
    expect(api.addPosition).not.toHaveBeenCalled()
    expect(wrapper.emitted('added')).toBeFalsy()
  })

  it('sendet gültige Position und emittiert "added"', async () => {
    vi.mocked(api.addPosition).mockResolvedValue()
    const wrapper = mount(AddPositionForm)

    await wrapper.find('#isin').setValue('IE00B4L5Y983')
    await wrapper.find('#quantity').setValue('10')
    await wrapper.find('#buyInPrice').setValue('90')
    await wrapper.find('form').trigger('submit.prevent')
    await flushPromises()

    expect(api.addPosition).toHaveBeenCalledWith({ isin: 'IE00B4L5Y983', quantity: 10, buyInPrice: 90 })
    expect(wrapper.emitted('added')).toBeTruthy()
  })

  it('zeigt eine Fehlermeldung, wenn die API ablehnt', async () => {
    vi.mocked(api.addPosition).mockRejectedValue(new Error('Ungültige Eingaben'))
    const wrapper = mount(AddPositionForm)

    await wrapper.find('#isin').setValue('X')
    await wrapper.find('#quantity').setValue('1')
    await wrapper.find('#buyInPrice').setValue('1')
    await wrapper.find('form').trigger('submit.prevent')
    await flushPromises()

    expect(wrapper.find('.msg.error').text()).toContain('Ungültige Eingaben')
    expect(wrapper.emitted('added')).toBeFalsy()
  })
})
